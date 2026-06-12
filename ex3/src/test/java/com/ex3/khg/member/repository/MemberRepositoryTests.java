package com.ex3.khg.member.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.ex3.khg.member.entity.MemberEntity;
import com.ex3.khg.member.exception.MemberExceptions;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    @Test
    public void testInsert() { // 등록 테스트
        for (int i = 1; i <= 100; i++) {
            MemberEntity memberEntity = MemberEntity.builder()
                .mid("user" + i)
                .mpw(passwordEncoder.encode("1111"))
                .mname("USER"+ i )
                .email("user"+i+"@aaa.com")
                .role(i <= 80 ? "USER": "ADMIN")
                .build();

            memberRepository.save(memberEntity);
        }
    }
     
    @Test
    public void testRead() { // 조회 테스트 (실패하고 메세지가 나오는게 정상)
        String mid = "user"; // 없는 id 

        Optional<MemberEntity> result = memberRepository.findById(mid);

        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        System.out.println(memberEntity);
    }

    @Test
    @Transactional
    @Commit
    public void testUpdate() { // 수정 테스트
        String mid = "user1";
        Optional<MemberEntity> result = memberRepository.findById(mid);

        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        memberEntity.changePassword(passwordEncoder.encode("2222"));
        memberEntity.changeName("수정된id");
    }
}
