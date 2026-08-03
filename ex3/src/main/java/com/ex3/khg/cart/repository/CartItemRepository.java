package com.ex3.khg.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.cart.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    
}
