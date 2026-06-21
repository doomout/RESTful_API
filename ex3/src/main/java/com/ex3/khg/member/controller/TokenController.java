package com.ex3.khg.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.member.dto.MemberDTO;
import com.ex3.khg.member.security.util.JWTUtil;
import com.ex3.khg.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {
    private final MemberService memberService;

    private final JWTUtil jwtUtil;
    
    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO) {
        log.info("make token...........");
        MemberDTO memberDTOResult = memberService.read(memberDTO.getMid(), memberDTO.getMpw());

        log.info(memberDTOResult);

        String mid = memberDTOResult.getMid();

        Map<String, Object> dataMap =  memberDTOResult.getDataMap();
        String accessToken = jwtUtil.createToken(dataMap, 10);
        String refreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);
        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);
        
        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(
        @RequestHeader("Authorization") String accessTokenStr,
        @RequestParam("refreshToken") String refreshToken,
        @RequestParam("mid") String mid
    ) {
        // 토큰 존재 확인
        log.info("Access Token with Bearer............"+ accessTokenStr);
        if(accessTokenStr == null || !accessTokenStr.startsWith("Bearer")) {
            return handleException("NO Access Token",  400);
        }

        if(refreshToken == null) {
            return handleException("No Refresh Token", 400);
        }

        log.info("refresh token...............", refreshToken);

        // mid 존재 확인
        if(mid == null) {
            return handleException("No Mid", 400); 
        }
        // Access Token 이 만료되었는지 확인
        String accessToken = accessTokenStr.substring(7);

        try {
            jwtUtil.validateToken(accessToken);
            // 아직 만료 기한이 남아 있는 상황
            Map<String, String> data = makeData(mid, accessToken, refreshToken);
            
            return ResponseEntity.ok(data);
        } catch(io.jsonwebtoken.ExpiredJwtException expiredJwtException) {
            //Refresh 가 필요한 상황
        } catch(Exception e) {
            return handleException(e.getMessage(), 400); 
        }
        // Refresh Token 검증
        // Refresh Token 에서 mid 검증
        // 새로운 Access Token, Refresh Token 생성
        // 전송
        return null;
    }
    
    private ResponseEntity<Map<String, String>> handleException(String msg, int status) {
        
        return ResponseEntity.status(status).body(Map.of("error", msg));
    }

    // Access Token 이 만료되지 않았는데 호출 되었을 때 새로운 토큰 발행 필요 없이 그대로 전송하는 메서드
    private Map<String, String> makeData(String mid, String accesssToken, String refreshToken) {
        return Map.of("mid", mid, "accessToken", accesssToken, "refreshToken", refreshToken);
    }
}
