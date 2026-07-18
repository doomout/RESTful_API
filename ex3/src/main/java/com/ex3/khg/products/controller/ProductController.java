package com.ex3.khg.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.products.dto.PageRequestDTO;
import com.ex3.khg.products.dto.ProductListDTO;
import com.ex3.khg.products.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;


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
    
}
