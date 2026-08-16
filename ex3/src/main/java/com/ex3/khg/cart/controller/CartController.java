package com.ex3.khg.cart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex3.khg.cart.dto.AddCartItemDTO;
import com.ex3.khg.cart.dto.CartItemDTO;
import com.ex3.khg.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    // Access Token의 mid 값과 AddCartItemDTO의 holder 값이 일치하는 경우에만 접근 허용
    @PreAuthorize("authentication.name == #addCartItemDTO.holder")
    @PostMapping("/addItem")
    public ResponseEntity<List<CartItemDTO>> addItem(@RequestBody AddCartItemDTO addCartItemDTO) {
        log.info("add item................");
        cartService.registerItem(addCartItemDTO);
        List<CartItemDTO> cartItems = cartService.getAllItems(addCartItemDTO.getHolder());

        return ResponseEntity.ok(cartItems);
    }
    // 장바구니 아이템의 조회
    @GetMapping("/{cno}")
    public ResponseEntity<List<CartItemDTO>> getCartItemList(@PathVariable("cno") Long cno, Principal principal) {
        log.info("get cart..............." + cno);
        String mid = principal.getName(); // 현재 로그인한 사용자의 mid 가져오기
        cartService.checkCartHolder(mid, cno);
        
        List<CartItemDTO> cartList = cartService.getAllItems(mid);
        
        return ResponseEntity.ok(cartList);
    }
    
}
