package com.ex3.khg.cart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
    private Long itemNo;
    private Long pNo;
    private String pName;
    private int quantity;
    private int price;
    private String image;
}
