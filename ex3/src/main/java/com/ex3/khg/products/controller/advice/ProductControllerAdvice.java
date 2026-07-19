package com.ex3.khg.products.controller.advice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ex3.khg.products.exception.ProductTaskException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice  // 모든 @RestController에서 발생하는 예외를 전역으로 처리
@Log4j2
public class ProductControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 예외 발생 로그 출력
        log.error("handlemethodArgumentNotValidException............");
        log.error(e.getMessage());

        // @Valid 검증에서 실패한 모든 에러 정보를 가져옴
        List<ObjectError> errors = e.getBindingResult().getAllErrors();

        // 여러 개의 에러 메시지를 하나의 문자열로 합침
        // 예) "상품명은 필수입니다, 가격은 0보다 커야 합니다"
        String errorMessage = errors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

        // HTTP 400(Bad Request) 상태와 함께 에러 메시지를 JSON 형태로 반환            
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String, String>> handleProductTaskException(ProductTaskException e) {
        log.error("ProductTaskException........");
        log.error(e.getClass().getName());
        log.error(e.getMessage());

        int status = e.getCode();

        return ResponseEntity.status(status).body(Map.of("error",e.getMessage()));
    }
}
