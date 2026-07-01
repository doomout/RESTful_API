package com.ex3.khg.upload.controller.advice;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.ex3.khg.upload.exception.UploadNotSupportedExcepiton;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class FileControllerAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return ResponseEntity.badRequest().body(Map.of("error","File too large"));
    }

    @ExceptionHandler(UploadNotSupportedExcepiton.class)
    public ResponseEntity<Map<String, String>> handleUploadNotSupportedException(UploadNotSupportedExcepiton exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
    }
}
