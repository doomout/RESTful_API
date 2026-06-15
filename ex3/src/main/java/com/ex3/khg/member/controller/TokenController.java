package com.ex3.khg.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.member.dto.MemberDTO;
import com.ex3.khg.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {
    private final MemberService memberService;
    
    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO) {
        log.info("make token...........");
        MemberDTO memberDTOResult = memberService.read(memberDTO.getMid(), memberDTO.getMpw());

        log.info(memberDTOResult);
        
        return null;
    }
    
}
