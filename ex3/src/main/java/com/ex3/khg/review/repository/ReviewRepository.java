package com.ex3.khg.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex3.khg.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    
}
