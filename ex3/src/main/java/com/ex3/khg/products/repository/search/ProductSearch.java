package com.ex3.khg.products.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.dto.ProductListDTO;

public interface ProductSearch  {
    Page<ProductListDTO> list(Pageable pageable);
    Page<ProductDTO>  listWithAllImages(Pageable pageable);
    Page<ProductDTO>  listFetchAllImages(Pageable pageable);

    Page<ProductListDTO> listWithReviewCount(Pageable pageable); // 리뷰의 개수
    Page<ProductDTO> listWithAllImagesReviewCount(Pageable pageable); // 상품의 모든 이미지와 리뷰의 갯수
}
