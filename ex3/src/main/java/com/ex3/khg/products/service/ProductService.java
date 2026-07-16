package com.ex3.khg.products.service;

import org.springframework.stereotype.Service;

import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.exception.ProductException;
import com.ex3.khg.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    // 등록된 상품을 모두 반환하도록 ProductDTO을 리턴 타입으로 사용
    public ProductDTO register(ProductDTO productDTO) {
        try {
            log.info("register.............");
            log.info(productDTO);

            ProductEntity productEntity = productDTO.toEntity();

            productRepository.save(productEntity);

            return new ProductDTO(productEntity);
        } catch(Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_REGISTERED.get();
        }
    }
}
