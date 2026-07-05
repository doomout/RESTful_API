package com.ex3.khg.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.products.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
}
