package com.ex3.khg.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
}
