package com.ex3.khg.products.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import com.ex3.khg.products.dto.ProductDTO;
import com.ex3.khg.products.dto.ProductListDTO;
import com.ex3.khg.products.entity.ProductEntity;
import com.ex3.khg.products.entity.QProductEntity;
import com.ex3.khg.products.entity.QProductImage;
import com.ex3.khg.review.entity.QReviewEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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

        // 조회할 컬럼들을 ProductListDTO에 매핑
        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(Projections.bean(ProductListDTO.class,
                productEntity.pno,
                productEntity.pname,
                productEntity.price,
                productEntity.writer,
                productImage.fileName.as("productImage")
            )
        );
        // Pageable에 들어있는 페이지 번호, 페이지 크기, 정렬 정보를 Query에 적용
        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);

        // 실제 조회 실행
        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        // 전체 데이터 개수 조회
        long count = dtojpqlQuery.fetchCount();

        // 조회된 데이터와 전체 데이터 개수를 PageImpl 객체로 반환
        return new PageImpl<>(dtoList, pageable, count); 
    }
    
    @Override
    public Page<ProductDTO> listWithAllImages(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;

        JPQLQuery<ProductEntity> query = from(productEntity);

        this.getQuerydsl().applyPagination(pageable, query);

        List<ProductEntity> entityList = query.fetch();
        long count = query.fetchCount();

        // for(ProductEntity entity : entityList) {
        //     System.out.println(entity);
        //     System.out.println(entity.getImages());
        //     System.out.println("-----------------------------");
        // }

        // ProductEntity를 ProductDTO로 변환
        List<ProductDTO> dtoList = entityList.stream().map(ProductDTO::new).toList();

        return new PageImpl<>(dtoList, pageable, count);
    }

    @Override
    public Page<ProductDTO> listFetchAllImages(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<ProductEntity> query = from(productEntity);

        // fetchJoin()을 사용하여 연관된 엔티티를 한 번의 쿼리로 가져오기
        query.leftJoin(productEntity.images, productImage).fetchJoin();

        this.getQuerydsl().applyPagination(pageable, query);

        List<ProductEntity> entityList = query.fetch();

        List<ProductDTO> dtoList = entityList.stream().map(ProductDTO::new).toList();

        long count = query.fetchCount();

        // for (ProductEntity entity : entityList) {
        //     System.out.println(entity);
        //     System.out.println(entity.getImages());
        //     System.out.println("-----------------------------");
        // }

        return new PageImpl<>(dtoList, pageable, count);
    }

    @Override
    public Page<ProductListDTO> listWithReviewCount(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;
        QReviewEntity reviewEntity = QReviewEntity.reviewEntity;

        JPQLQuery<ProductEntity> query = from(productEntity);

        // 리뷰 테이블과 LEFT JOIN (상품에 리뷰가 없어도 상품은 조회)
        query.leftJoin(reviewEntity).on(reviewEntity.productEntity.eq(productEntity));
        // 이미지도 LEFT JOIN
        query.leftJoin(productEntity.images, productImage);

        // 대표 이미지만 사용
        query.where(productImage.idx.eq(0));

        this.getQuerydsl().applyPagination(pageable, query);

        // 그룹핑 필요 (집계함수 사용 시)
        query.groupBy(productEntity);

        // Projection으로 필요한 필드와 리뷰 개수를 매핑
        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(
            Projections.bean(ProductListDTO.class,
                productEntity.pno,
                productEntity.pname,
                productEntity.price,
                productEntity.writer,
                productImage.fileName.as("productImage"),
                reviewEntity.countDistinct().as("reviewCount")
        ));

        // 페이징 적용 후 조회
        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);
        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        long count = dtojpqlQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, count);
    }

    @Override
    public Page<ProductDTO> listWithAllImagesReviewCount(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;
        QReviewEntity reviewEntity = QReviewEntity.reviewEntity;

        // ProductEntity 기준 쿼리 생성
        JPQLQuery<ProductEntity> query = from(productEntity);
        // 리뷰와 LEFT JOIN (상품별 리뷰 수 집계용)
        query.leftJoin(reviewEntity).on(reviewEntity.productEntity.eq(productEntity));

        // 페이징과 그룹핑 적용 (집계 필요)
        this.getQuerydsl().applyPagination(pageable, query);
        query.groupBy(productEntity);

        // ProductEntity와 리뷰 카운트를 함께 조회 (Tuple 사용)
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(productEntity, reviewEntity.countDistinct());

        List<Tuple> result = tupleJPQLQuery.fetch();

        // Tuple을 ProductDTO로 변환하여 리뷰 개수를 DTO에 설정
        List<ProductDTO> dtoList = result.stream().map(tuple -> {
            ProductEntity product = tuple.get(0, ProductEntity.class);
            long count = tuple.get(1, Long.class);

            ProductDTO dto = new ProductDTO(product);
            dto.setReviewCount(count);

            return dto;
        }).toList();

        // 페이징 결과 반환
        return new PageImpl<>(dtoList, pageable, tupleJPQLQuery.fetchCount());
    }
}
