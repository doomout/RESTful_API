package com.ex3.khg.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.cart.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    // 소유주로 CartEntity를 조회하는 메서드
    Optional<CartEntity> findByHolder(String holder);
}
