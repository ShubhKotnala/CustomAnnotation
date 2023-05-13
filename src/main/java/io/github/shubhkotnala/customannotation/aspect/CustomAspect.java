package io.github.shubhkotnala.customannotation.aspect;

import io.github.shubhkotnala.customannotation.annotation.RequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class CustomAspect {

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(requestLogger)")
    public Object logRequest(ProceedingJoinPoint joinPoint, RequestLogger requestLogger) throws Throwable {
        if (requestLogger.enabled()) {
            log.info("{} : Request received", request.getRequestURI());

            //Extracting method signature
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

            //Extracting method arguments name
            String[] parameterNames = codeSignature.getParameterNames();

            //Extracting method arguments value
            Object[] args = joinPoint.getArgs();

            Map<String, Object> fieldToValue = new HashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                fieldToValue.put(parameterNames[i], args[i]);
            }
            fieldToValue.forEach((key, value) -> log.info("Arg : {} = {}", key, value));
            Object obj = joinPoint.proceed();
            log.info("{} : Request finished", request.getRequestURI());
            return obj;
        } else {
            log.warn("{} : Request received but logging is disabled", request.getRequestURI());
            return joinPoint.proceed();
        }
    }

}
