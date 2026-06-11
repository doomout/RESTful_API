package com.ex3.khg.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    
}
