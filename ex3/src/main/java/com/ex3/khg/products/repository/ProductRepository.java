package com.ex3.khg.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex3.khg.products.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // images 속성을 즉시 로딩으로 가져오기 위해 EntityGraph를 사용
    @EntityGraph(attributePaths = "images", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT p FROM ProductEntity p WHERE p.pno = :pno")
    Optional<ProductEntity> findByIdWithImages(@Param("pno") Long pno); 
}
