package io.github.shubhkotnala.customannotation.Controller;

import io.github.shubhkotnala.customannotation.annotation.RequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TestController {

    @GetMapping
    @RequestLogger
    public String test() {
        log.info("Inside API method");
        return "Hello World";
    }

    @PostMapping
    @RequestLogger
    public String testParam(@RequestBody String body, @RequestParam String param) {
        log.info("Inside API method with body : {} param: {}", body, param);
        return body;
    }

}
