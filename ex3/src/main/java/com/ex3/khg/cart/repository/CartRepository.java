package com.ex3.khg.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.cart.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    
}
