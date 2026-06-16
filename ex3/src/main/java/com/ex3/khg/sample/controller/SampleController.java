package com.ex3.khg.sample.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Log4j2
@RequestMapping("/api/v1/samples")
public class SampleController { // 접근 제한 테스트를 위한 컨트롤러
    @GetMapping("/list")
    public ResponseEntity<?> list() {
        log.info("list............");
        String[] arr = {"AAA", "BBB", "CCC"};
        return ResponseEntity.ok(arr);
    }
    
}
