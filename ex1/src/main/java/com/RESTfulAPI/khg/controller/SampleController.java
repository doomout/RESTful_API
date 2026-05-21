package com.RESTfulAPI.khg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/sample")
@Log4j2
public class SampleController {
    @RequestMapping("/hello")
    public String[] hello() {
        return new String[]{"Hello, World!"};
    }
}
