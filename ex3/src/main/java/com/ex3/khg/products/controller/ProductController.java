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
import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    // 상품 목록 처리
    @GetMapping("/list")
    public ResponseEntity<Page<ProductListDTO>> list(@Validated PageRequestDTO pageRequestDTO, Principal principal) {
        log.info(pageRequestDTO);
        log.info(principal.getName());

        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }
    
    // 상품 등록 처리
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
    
    // 상품 조회 처리
    @GetMapping("/{pno}")
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") Long pno) {
        log.info("read..............");
        log.info(pno);

        ProductDTO productDTO = productService.read(pno);
        
        return ResponseEntity.ok(productDTO);
    }
    
    // 상품 삭제 처리(현재 사용자, admin 권한자만 가능)
    @DeleteMapping("/{pno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("pno") Long pno, Authentication authentication) {
        log.info("remove.............");
        log.info(pno);
        log.info(authentication.getName());
        log.info(authentication.getAuthorities());

        ProductDTO productDTO = productService.read(pno);

        if(!productDTO.getWriter().equals(authentication.getName())) {
            // 현재 사용자의 권한
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            // ADMIN 권한이 없는 경우 예외 발생
            authorities.stream()
                    .filter(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
                    .findAny()
                    .orElseThrow(ProductException.PRODUCT_WRITER_ERROR::get);
        }

        productService.remove(pno);

        return ResponseEntity.ok(Map.of("result", "success"));
    } 
}
