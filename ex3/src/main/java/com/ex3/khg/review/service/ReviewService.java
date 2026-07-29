package com.ex3.khg.review.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.review.dto.ReviewDTO;
import com.ex3.khg.review.entity.ReviewEntity;
import com.ex3.khg.review.exception.ReviewException;
import com.ex3.khg.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 등록처리
    public ReviewDTO register(ReviewDTO reviewDTO) {
        log.info("review register.........");

        try{
            ReviewEntity reviewEntity = reviewDTO.toEntity();

            reviewRepository.save(reviewEntity);

            return new ReviewDTO(reviewEntity);
        }catch(DataIntegrityViolationException e){
            // 외래키 위반
            throw ReviewException.REVIEW_PRODUCT_NOT_FOUND.get();
        }catch(Exception e){
            log.error(e.getMessage());
            throw ReviewException.REVIEW_NOT_REGISTERED.get();
        }
    }
}
