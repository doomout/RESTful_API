package com.ex3.khg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ex3.khg.member.security.filter.JWTCheckFilter;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableMethodSecurity(prePostEnabled = true) // 메서드 단위로 권한 검사하는 기능을 켜겠다
public class CustomSecurityConfig {
    private JWTCheckFilter jwtCheckFilter;

    @Autowired
    private void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
        this.jwtCheckFilter = jwtCheckFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("filter chain......");

        // REST API 방식에서는 기본 로그인 화면을 사용하지 않으므로 폼 로그인을 비활성화합니다.
        httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer.disable();
        });

        // 서버에서 세션 기반 로그아웃을 처리하지 않도록 로그아웃 기능을 비활성화합니다.
        httpSecurity.logout(config -> config.disable());

        // API 요청은 별도의 토큰/인증 방식으로 처리할 수 있도록 CSRF 보호를 비활성화합니다.
        httpSecurity.csrf(config -> {
            config.disable();
        });

        // 기존 세션이 있으면 사용하지만, Spring Security가 새 세션을 만들지는 않습니다.
        httpSecurity.sessionManagement(sessionManagementConfigurer -> {
            sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });

        // jwtCheckFilter 를 UsernamePasswordAuthenticationFilter 앞에 두기
        httpSecurity.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

        // 위 설정을 기반으로 Spring Security 필터 체인을 생성합니다.
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 회원 비밀번호를 안전하게 저장하기 위해 BCrypt 해시 알고리즘을 사용합니다.
        return new BCryptPasswordEncoder();
    }
}
