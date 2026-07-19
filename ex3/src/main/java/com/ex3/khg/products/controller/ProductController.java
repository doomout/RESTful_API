package com.ex3.khg.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.products.dto.PageRequestDTO;
import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.dto.ProductListDTO;
import com.ex3.khg.products.exception.ProductException;
import com.ex3.khg.products.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<Page<ProductListDTO>> list(@Validated PageRequestDTO pageRequestDTO, Principal principal) {
        log.info(pageRequestDTO);
        log.info(principal.getName());

        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }
    
    @PostMapping("")
    public ResponseEntity<ProductDTO> register(@RequestBody @Validated ProductDTO productDTO, Principal principal) {
        log.info("register.............");
        log.info(productDTO);
        
        // 이미지 파일이 없는 경우
        if(productDTO.getImageList() == null || productDTO.getImageList().isEmpty()) {
            throw ProductException.PRODUCT_NO_IMAGE.get();
        }

        // 쓴 사람과 현재 사용자가 다를 때
        if(!principal.getName().equals(productDTO.getWriter())) {
            throw ProductException.PRODUCT_WRITER_ERROR.get();
        }
        
        return ResponseEntity.ok(productService.register(productDTO));
    }
    
}
