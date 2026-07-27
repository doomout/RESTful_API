package com.ex3.khg.products.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {
    // images 속성을 즉시 로딩으로 가져오기 위해 EntityGraph를 사용
    // @EntityGraph(attributePaths = "images", type = EntityGraph.EntityGraphType.FETCH)
    // @Query("SELECT p FROM ProductEntity p WHERE p.pno = :pno")
    // Optional<ProductEntity> getProduct(@Param("pno") Long pno); 

    // Fetch Join(JPA 에서만 사용)로 images 속성을 즉시 로딩으로 가져오기
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.images pi WHERE p.pno = :pno")
    Optional<ProductEntity> getProduct(@Param("pno") Long pno);

    // ProductDTO를 반환하도록 JPQL 쿼리 작성
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.images pi WHERE p.pno = :pno")
    Optional<ProductDTO> getProductDTO(@Param("pno") Long pno);

    // 검색이 없는 경우의 Fetch Join 
    @Query("select p from ProductEntity p join fetch p.images pi")
    Page<ProductDTO> listQuery(Pageable pageable);
}
