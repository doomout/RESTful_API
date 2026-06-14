package com.ex3.khg.member.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.member.dto.MemberDTO;
import com.ex3.khg.member.entity.MemberEntity;
import com.ex3.khg.member.exception.MemberExceptions;
import com.ex3.khg.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO read(String mid, String mpw) {
        Optional<MemberEntity> result = memberRepository.findById(mid);
        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);

        if(!passwordEncoder.matches(mpw, memberEntity.getMpw())) {
            throw MemberExceptions.BAD_CREDENTIALS.get();
        }

        return new MemberDTO(memberEntity);
    }
}
