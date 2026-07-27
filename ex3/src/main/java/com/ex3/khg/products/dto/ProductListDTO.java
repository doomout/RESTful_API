package com.ex3.khg.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {
    private Long pno;
    private String pname;
    private int price;
    private String writer;
    private String productImage; // 상품 이미지 파일 이름

    private Long reviewCount; // 리뷰 갯수
}
