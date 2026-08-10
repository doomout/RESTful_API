package com.ex3.khg.member.security.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

// Spring Bean으로 등록
// 다른 클래스에서 @Autowired 또는 생성자 주입으로 사용 가능
@Component
@Log4j2
public class JWTUtil {
    @Value("${jwt.secret:change_this_jwt_secret_to_a_long_secret}")
    private String key;
    /*
     * JWT 토큰 생성 메서드
     * valueMap : 토큰 안에 넣을 데이터
     * min : 토큰 유지 시간(분)
     */
    public String createToken(
            Map<String, Object> valueMap,
            int min) {


        // JWT 서명에 사용할 암호화 키 객체
        SecretKey secretKey = null;


        try {

            /*
             * 문자열 비밀키를
             * HMAC-SHA 알고리즘용 SecretKey 객체로 변환
             *
             * HS256 서명할 때 필요
             */
            secretKey = Keys.hmacShaKeyFor(
                    this.key.getBytes("UTF-8")
            );
        } catch (Exception e) {
            // 키 생성 실패 시 RuntimeException 발생
            throw new RuntimeException(e.getMessage());
        }
        // JWT 생성 시작 
        return Jwts.builder()
            //  ===== Header 부분 =====
            .header()
                // 토큰 타입 지정
                .add("typ", "JWT")
                // 암호화 알고리즘 지정
                .add("alg", "HS256")
            // header 설정 종료 후 builder 복귀
            .and()
            //===== Payload 기본 정보 =====
            /*
             * 토큰 생성 시간
             *
             * iat:
             * issued at
             */
            .issuedAt(
                Date.from(
                    ZonedDateTime
                        .now()
                        .toInstant()
                )
            )

            /*
             * 토큰 만료 시간
             *
             * 현재시간 + min
             *
             * exp:
             * expiration
             */
            .expiration(
                Date.from(
                    ZonedDateTime
                        .now()
                        .plusMinutes(min)
                        .toInstant()
                )
            )

            // 사용자가 넣고 싶은 데이터 저장
            .claims(valueMap)

            /*
             * ===== Signature 생성 =====
             *
             * Header + Payload 내용을
             * SecretKey로 암호화 서명
             *
             * 이걸 이용해서 나중에
             * 위조 여부 검사
             */
            .signWith(secretKey)

            /*
             * 최종 문자열 JWT 생성
             * 결과: xxxxx.yyyyy.zzzzz
             */
            .compact();
    }

    /*
     * JWT 검증 메서드
     *
     * 클라이언트가 보내온 token이:
     *
     * - 내가 만든 게 맞는지
     * - 변조되지 않았는지
     * - 만료되지 않았는지
     *
     * 확인
     */
    public Map<String, Object> validateToken(String token) {
        SecretKey secretKey = null;

        try {
            /*
             * 생성 때 사용했던 것과
             * 동일한 SecretKey 생성
             */
            secretKey = Keys.hmacShaKeyFor(
                    this.key.getBytes("UTF-8")
            );
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
        // JWT 파싱 + 검증
        Claims claims = Jwts.parser()
            /*
             * 이 키로 서명 확인
             *
             * 생성 때 사용한 key와 다르면 실패
             */
            .setSigningKey(secretKey)

            // parser 생성 완료
            .build()

            // JWT 검증 실행
            .parseClaimsJws(token)
            // Payload 부분 가져오기
            .getBody();

        // Payload 출력
        log.info("claims: " + claims);

        // Claims는 Map 구조라 그대로 반환 가능
        return claims;
    }
}