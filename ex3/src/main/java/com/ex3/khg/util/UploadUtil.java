package com.ex3.khg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class UploadUtil {
    @Value("${com.ex3.khg.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);

        if(tempFolder.exists() == false) {
            tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();

        log.info("----------------------------------------");
        log.info(uploadPath);
    }

    public List<String> upload(MultipartFile[] files) {
        List<String> result = new ArrayList<>();

        for(MultipartFile file : files) {
            log.info("----------------------------------------");
            log.info("name: " + file.getOriginalFilename());

            // 이미지 파일만 업로드 가능하도록 체크
            if(file.getContentType().startsWith("image") == false) {
                log.error("File type not supported:" + file.getContentType());
                continue;
            }
            // 파일명 중복 방지를 위해 UUID를 이용하여 파일명 생성
            String uuid = UUID.randomUUID().toString();
            String saveFileName = uuid + "_" + file.getOriginalFilename();

            // 파일 업로드
            try (InputStream in = file.getInputStream(); 
                 OutputStream out = new FileOutputStream(new File(uploadPath, File.separator +  saveFileName))
            ) {
                FileCopyUtils.copy(in, out);
                result.add(saveFileName);    
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return result;
    }
}
