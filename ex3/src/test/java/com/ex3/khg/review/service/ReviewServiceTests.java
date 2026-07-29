package com.ex3.khg.review.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex3.khg.review.dto.ReviewDTO;

@SpringBootTest
public class ReviewServiceTests {
    @Autowired
    private ReviewService reviewService;

    // 등록 테스트
    @Test
    public void testRegister() {
        Long pno = 999L; //존재 하지 않는 상품 번호
        ReviewDTO reviewDTO = ReviewDTO.builder()
                    .reviewText("리뷰내용")
                    .score(5)
                    .reviewer("악플러")
                    .pno(pno)
                    .build();

        reviewService.register(reviewDTO);
    }
}
