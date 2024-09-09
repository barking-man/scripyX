package com.mark.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RequestMapping("/test/server")
@RestController
@Slf4j
public class TestServerController {
    @Value("${TEST_VALUE}")
    private String TEST_VALUE;

    @RequestMapping(value = "/getTestValue", method = RequestMethod.GET)
    public String getTestValue() {
        log.info("info级别日志");
        log.debug("debug 日志级别");
        return TEST_VALUE;
    }
}
