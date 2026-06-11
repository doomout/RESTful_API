package com.todo.khg.member.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todo.khg.entity.MemberEntity;
import com.todo.khg.member.exception.MemberExceptions;
import com.todo.khg.repository.MemberRepository;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        for (int i = 1; i <= 100; i++) {
            MemberEntity memberEntity = MemberEntity.builder()
                    .mid("user" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .mname("USER" + i)
                    .email("user" + i + "@aaa.com")
                    .role(i <= 80 ? "USER" : "ADMIN") // user1 ~ user80 : USER, user81 ~ user100 : ADMIN
                    .build();

            memberRepository.save(memberEntity);
        } 
    }

    @Test
    public void testRead() { // 아이디가 없는 경우 테스트
        String mid = "user1";

        Optional<MemberEntity> result = memberRepository.findById(mid);

        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        System.out.println(memberEntity);
    }
}
