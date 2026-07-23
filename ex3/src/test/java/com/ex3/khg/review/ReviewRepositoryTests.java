package com.ex3.khg.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.review.entity.ReviewEntity;
import com.ex3.khg.review.repository.ReviewRepository;

@SpringBootTest
//@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 등록 테스트
    @Test
    public void testInsert() {
        Long pno = 38L;
        ProductEntity productEntity = ProductEntity.builder().pno(pno).build();

        ReviewEntity reviewEntity = ReviewEntity.builder()
            .reviewText("리뷰 내용....")
            .score(5)
            .reviewer("reviewer1")
            .productEntity(productEntity)
            .build();

        reviewRepository.save(reviewEntity);
    }
    // 리뷰 조회 테스트
    @Test
    @Transactional
    public void testRead() {
        Long rno = 36L;
        reviewRepository.findById(rno).ifPresent(reviewEntity -> {
            System.out.println(reviewEntity);
            System.out.println(reviewEntity.getProductEntity());
        });
    }
}
