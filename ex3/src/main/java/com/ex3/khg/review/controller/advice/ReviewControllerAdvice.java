package com.ex3.khg.review.controller.advice;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ex3.khg.review.exception.ReviewTaskException;

@RestControllerAdvice
public class ReviewControllerAdvice {
    @ExceptionHandler(ReviewTaskException.class)
    public ResponseEntity<Map<String, String>> handleReviewTaskException(ReviewTaskException exception) {
        int status = exception.getCode();
        String message = exception.getMessage();

        return ResponseEntity.status(status).body(Map.of("error", message));
    }
}
