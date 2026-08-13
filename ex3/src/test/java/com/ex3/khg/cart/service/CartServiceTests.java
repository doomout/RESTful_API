package com.ex3.khg.cart.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex3.khg.cart.dto.AddCartItemDTO;
import com.ex3.khg.cart.dto.CartItemDTO;
import com.ex3.khg.cart.dto.ModifyCartItemDTO;

@SpringBootTest
public class CartServiceTests {
    @Autowired
    private CartService cartService;

    @Test
    public void testGetCartList() {
        String mid = "user22";

        List<CartItemDTO> cartItems = cartService.getAllItems(mid);

        cartItems.forEach(cartItemDTO -> {
            System.out.println(cartItemDTO);
        });
    }

    @Test
    public void testRegisterItem() {
        String mid = "user22";
        Long pno = 40L;
        int qty = 2;

        AddCartItemDTO addCartItemDTO = AddCartItemDTO.builder()
                .holder(mid)
                .pno(pno)
                .quantity(qty)
                .build();

        cartService.registerItem(addCartItemDTO);
    }

    @Test
    public void testModifyItem() { // 장바구니 아이템 수량 수정 테스트
        Long itemNo = 2L;
        int qty = 1;

        ModifyCartItemDTO modifyCartItemDTO = ModifyCartItemDTO.builder()
                .itemNo(itemNo)
                .quantity(qty)
                .build();

        cartService.modifyItem(modifyCartItemDTO);
    }
}
