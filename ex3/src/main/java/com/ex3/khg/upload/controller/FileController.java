package com.ex3.khg.upload.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ex3.khg.upload.exception.UploadNotSupportedExcepiton;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Log4j2
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
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
    }
}
