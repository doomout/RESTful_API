package com.ex3.khg.cart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModifyCartItemDTO { // 장바구니 수정을 위한 DTO
    private Long itemNo; // 장바구니 아이템 번호
    private int quantity; // 수량
}
