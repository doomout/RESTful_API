package com.ex3.khg.review.controller;

import java.security.Principal;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.review.dto.ReviewDTO;
import com.ex3.khg.review.exception.ReviewException;
import com.ex3.khg.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 등록 처리
    @PostMapping("")
    public ResponseEntity<ReviewDTO> register(@RequestBody @Validated ReviewDTO reviewDTO, Principal principal) {
        log.info("register: " + reviewDTO);
        if(!principal.getName().equals(reviewDTO.getReviewer())) {
            throw ReviewException.REVIEW_MISMATCH.get();
        }
        return ResponseEntity.ok(reviewService.register(reviewDTO));
    }

    // 리뷰 조회 처리
    @GetMapping("/{rno}")
    public ResponseEntity<ReviewDTO> read(@PathVariable("rno") Long rno) {
        log.info("read: " + rno);
        
        return ResponseEntity.ok(reviewService.read(rno));
    }

    // 리뷰 삭제 처리
    @DeleteMapping("/{rno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("rno") Long rno, Authentication authentication) {
        log.info("remove: "+ rno);
        String currentUser = authentication.getName();
        log.info("currentUser: " + currentUser);
        ReviewDTO reviewDTO = reviewService.read(rno);

        if(!currentUser.equals(reviewDTO.getReviewer())) {
            throw ReviewException.REVIEW_MISMATCH.get();
        }
        reviewService.remove(rno);

        return ResponseEntity.ok().body(Map.of("result", "success"));
    }
    
}
