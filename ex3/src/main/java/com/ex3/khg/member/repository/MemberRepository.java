package com.ex3.khg.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.member.entity.MemberEntity;

// MemberEntity의 기본 CRUD와 조회 기능을 제공하는 Spring Data JPA 저장소입니다.
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    
}
