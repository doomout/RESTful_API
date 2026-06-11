package com.todo.khg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todo.khg.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    
}
