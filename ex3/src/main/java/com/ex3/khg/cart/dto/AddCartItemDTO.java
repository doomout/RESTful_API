package com.ex3.khg.cart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemDTO {
    private String holder; // 소유주
    private Long pno; // 상품 번호
    private int quantity; // 수량
}
