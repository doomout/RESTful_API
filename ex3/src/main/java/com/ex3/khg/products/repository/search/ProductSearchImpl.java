package com.ex3.khg.products.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.ex3.khg.products.dto.ProductListDTO;
import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.entity.QProductEntity;
import com.ex3.khg.products.entity.QProductImage;
import com.querydsl.jpa.JPQLQuery;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    @Override
    public Page<ProductListDTO> list(Pageable pageable) {
        // QueryDSL에서 사용할 Q클래스(테이블 역할)
        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;

        // ProductEntity를 기준으로 조회 시작 (SELECT * FROM ProductEntity)
        JPQLQuery<ProductEntity> query = from(productEntity);

        // 상품과 상품 이미지를 LEFT JOIN 이미지가 없는 상품도 조회되도록 LEFT JOIN 사용
        query.leftJoin(productEntity.images, productImage);

        // 대표 이미지(idx = 0)만 조회 하나의 상품에 이미지가 여러 장 있으므로 첫 번째 이미지만 가져오기 위함
        query.where(productImage.idx.eq(0));

        // Pageable에 들어있는 페이지 번호, 페이지 크기, 정렬 정보를 Query에 적용
        this.getQuerydsl().applyPagination(pageable, query);

        // 실제 조회 실행
        query.fetch();

        // 전체 데이터 개수 조회
        query.fetchCount();

        return null; 
    }
    
}
