package com.todo.khg.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.todo.khg.exception.EntityNotFoundException;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class APIControllerAdvice { // API 컨트롤러에서 발생하는 예외를 처리하는 클래스
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // 매개변수의 타입이 일치하지 않을 때 발생하는 예외를 처리하는 메서드
    public ResponseEntity<?> handleArgsException(MethodArgumentTypeMismatchException exception) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", "Type Mismatch");
        errors.put(exception.getName(), exception.getValue() + " is not valid value");

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class) // 엔티티를 찾을 수 없을 때 발생하는 예외를 처리하는 메서드
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception) { 
        Map<String, String> errors = Map.of("message", "Entity Not Found");

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class) // 매개변수의 유효성 검사가 실패할 때 발생하는 예외를 처리하는 메서드
    public ResponseEntity<?> handleArgsException(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
