package com.ex3.khg.support;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.ex3.khg.member.security.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestTokenHelper {

    private final JWTUtil jwtUtil;

    public String accessToken(String mid, String role) {
        return jwtUtil.createToken(
                Map.of(
                        "mid", mid,
                        "mname", mid,
                        "email", mid + "@test.com",
                        "role", role),
                10);
    }

    public RequestPostProcessor bearerAuth(String mid, String role) {
        String token = accessToken(mid, role);

        return request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        };
    }
}
