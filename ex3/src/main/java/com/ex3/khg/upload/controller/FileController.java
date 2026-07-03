package com.ex3.khg.upload.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ex3.khg.upload.exception.UploadNotSupportedExcepiton;
import com.ex3.khg.util.UploadUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Log4j2
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final UploadUtil uploadUtil;

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

        List<String> result = uploadUtil.upload(files);

        return ResponseEntity.ok(result);
    }

    private void checkFileType(String fileName) throws UploadNotSupportedExcepiton {
        // jpg, gif, png, bmp
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String regExp = "^(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF|bmp|BMP)";

        if(!suffix.matches(regExp)) {
            throw new UploadNotSupportedExcepiton("File type not supported: " + suffix);
        }
    }

    // 파일 삭제
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Void> deleteFile(@PathVariable(name = "fileName") String fileName) {
        log.info("delete file: " + fileName);
        uploadUtil.deleteFile(fileName);

        return ResponseEntity.ok().build();
    }
}
