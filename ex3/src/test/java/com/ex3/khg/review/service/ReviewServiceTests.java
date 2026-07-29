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
                    .reviewText("이게 등록되면 안된다.")
                    .score(5)
                    .reviewer("알겠냐")
                    .pno(pno)
                    .build();

        reviewService.register(reviewDTO); // 404 에러 나는게 정상
    }

    // 리뷰 조회 테스트
    @Test
    public void testRead(){
        Long rno = 1L; // 존재하는 리뷰 번호
        ReviewDTO reviewDTO = reviewService.read(rno);
        System.out.println(reviewDTO);
    }
}
