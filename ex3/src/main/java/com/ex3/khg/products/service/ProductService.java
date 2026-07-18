package com.ex3.khg.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.products.dto.PageRequestDTO;
import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.dto.ProductListDTO;
import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.exception.ProductException;
import com.ex3.khg.products.repository.ProductRepository;

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

    @Transactional(readOnly = true)
    public ProductDTO read(Long pno) {
        log.info("read..............");
        log.info(pno);

        Optional<ProductEntity> result = productRepository.getProduct(pno);

        ProductEntity productEntity = result.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        return new ProductDTO(productEntity);
    }

    public void remove(Long pno) {
        log.info("remove..........");
        log.info(pno);

        Optional<ProductEntity> result = productRepository.findById(pno);
        ProductEntity productEntity = result.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        try{
            productRepository.delete(productEntity);
        }catch(Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_REMOVED.get();
        }
    }

    //상품 수정(기존 상품 이미지는 전부 삭제하고 새로운 이미지로 다시 업로드)
    public ProductDTO modify(ProductDTO productDTO) {
        log.info("modify..............");
        log.info(productDTO);

        Optional<ProductEntity> result = productRepository.findById(productDTO.getPno());

        ProductEntity productEntity = result.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

        try {
            // 상품 정보 수정
            productEntity.changePrice(productDTO.getPrice());
            productEntity.changeTitle(productDTO.getPname());
            
            // 기존 이미지들 삭제
            productEntity.clearImages();

            // 새로운 이미지들 추가
            List<String> fileNames = productDTO.getImageList();
            if(fileNames != null && !fileNames.isEmpty()) {
                fileNames.forEach(productEntity::addImage);
            }
            productRepository.save(productEntity);

            return new ProductDTO(productEntity);
        }catch(Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_MODIFIED.get();
        }
    }

    // 상품 목록
    public Page<ProductListDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("getList.............");
        log.info(pageRequestDTO);

        try {
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("pno").descending());

            return productRepository.list(pageable);
        }catch(Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_FETCHED.get();
        }
    }
}
