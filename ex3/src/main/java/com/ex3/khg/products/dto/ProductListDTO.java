package com.ex3.khg.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// 상품의 이미지는 하나만 담을 수 있도록 구성
@Data
@NoArgsConstructor
public class ProductListDTO {
    private Long pno;
    private String pname;
    private int price;
    private String writer;
    private String productImage; // 상품 이미지 파일 이름
}
