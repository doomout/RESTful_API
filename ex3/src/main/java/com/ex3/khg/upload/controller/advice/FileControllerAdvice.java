package com.ex3.khg.upload.controller.advice;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files) {
        log.info("upload file.....");
        if(files == null || files.length == 0) {
            throw new UploadNotSupportedExcepiton("No files uploaded");
        }
        for(MultipartFile file : files) {
            log.info("-----------------------------------------");
            log.info("name: " + file.getOriginalFilename());
            checkFileType(file.getOriginalFilename());
        }

        return null;
    }

    private void checkFileType(String fileName) throws UploadNotSupportedExcepiton {
        // jpg, gif, png, bmp
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String regExp = "^(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF|bmp|BMP)";

        if(!suffix.matches(regExp)) {
            throw new UploadNotSupportedExcepiton("File type not supported: " + suffix);
        }

        throw new UnsupportedOperationException("Unimplemented method 'checkFileType'");
    }
}
