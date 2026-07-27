package com.ex3.khg.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.review.entity.ReviewEntity;
import com.ex3.khg.review.exception.ReviewException;
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

    // fetch join 방식으로 리뷰, 상품, 이미지 까지 나오는지 테스트
    @Test
    public void testGetWithProduct() {
        Long rno = 36L; // 리뷰가 있는 상품 번호

        reviewRepository.getWithProdcut(rno).ifPresent(reviewEntity -> {
            System.out.println(reviewEntity);
            System.out.println(reviewEntity.getProductEntity());
            System.out.println(reviewEntity.getProductEntity().getImages()); // 상품 이미지도 출력
        });
    }

    // 리뷰 삭제 테스트
    @Test
    @Transactional
    @Commit
    public void testRemove() {
        Long rno = 36L;

        reviewRepository.deleteById(rno);
    }

    // 리뷰 삭제 후 해당 리뷰가 존재하는지 체크하는 테스트
    @Test
    @Transactional
    @Commit
    public void testRemove2() {
        ReviewEntity reviewEntity = reviewRepository.findById(36L).orElseThrow(ReviewException.REVIEW_NOT_FOUND::get);

        reviewRepository.delete(reviewEntity);
    }

    // 리뷰 수정
    @Test
    @Transactional
    @Commit
    public void testUpdate() {
        Long rno = 36L;

        ReviewEntity reviewEntity = reviewRepository.findById(rno).orElseThrow(ReviewException.REVIEW_NOT_FOUND::get);

        reviewEntity.changeReviewText("변경된 리뷰 내용");
        reviewEntity.changeScore(3);
    }

    // 리뷰 목록
    @Test
    public void testList() {
        Long pno = 36L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        reviewRepository.getListByPno(pno, pageable).getContent()
            .forEach(reviewDTO -> {
                System.out.println(reviewDTO);
            });
    }
}
