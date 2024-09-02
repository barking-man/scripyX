package com.mark.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RequestMapping("/test/server")
@RestController
public class TestServerController {
    @Value("${TEST_VALUE}")
    private String TEST_VALUE;

    @RequestMapping(value = "/getTestValue", method = RequestMethod.GET)
    public String getTestValue() {
        return TEST_VALUE;
    }
}
