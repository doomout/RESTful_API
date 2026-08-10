package com.ex3.khg.cart.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ex3.khg.cart.dto.CartItemDTO;

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
}
