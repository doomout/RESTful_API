package com.ex3.khg.review.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex3.khg.review.dto.ReviewDTO;
import com.ex3.khg.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    // 리뷰, 상품 정보, 이미지를 같이 불러올 때 지연 로딩은 여러번 select가 발생하니 
    // fetch join 으로 한번에 불러오기
    @Query("select r from ReviewEntity r " + 
            " join fetch r.productEntity rp " + 
            " join fetch rp.images " + 
            " where r.rno = :rno")
    Optional<ReviewEntity> getWithProdcut(@Param("rno") Long rno);

    //ProductEntity 의 번호를 이용해서 리뷰 목록을 찾는 기능
    @Query("select r from ReviewEntity r where r.productEntity.pno = :pno")
    Page<ReviewDTO> getListByPno(@Param("pno") Long pno, Pageable pageable);
}
