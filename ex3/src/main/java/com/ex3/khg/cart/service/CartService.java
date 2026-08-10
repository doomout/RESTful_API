package com.ex3.khg.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.cart.dto.CartItemDTO;
import com.ex3.khg.cart.entity.CartItemEntity;
import com.ex3.khg.cart.repository.CartItemRepository;
import com.ex3.khg.cart.repository.CartRepository;
import com.ex3.khg.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CartItemDTO> getAllItems(String mid) {
        List<CartItemDTO> itemDTOList = new ArrayList<>();

        Optional<List<CartItemEntity>> result = cartItemRepository.getCartItemsOfHolder(mid);

        if(result.isEmpty()) {
            return itemDTOList;    
        }
        List<CartItemEntity> cartItemEntityList = result.get();

        cartItemEntityList.forEach(cartItemEntity -> {
            itemDTOList.add(entityToDTO(cartItemEntity));
        });

        return itemDTOList;
    }

    private CartItemDTO entityToDTO(CartItemEntity cartItemEntity) {
        return CartItemDTO.builder()
                .itemNo(cartItemEntity.getItemNo())
                .pname(cartItemEntity.getProduct().getPname())
                .pno(cartItemEntity.getProduct().getPno())
                .price(cartItemEntity.getProduct().getPrice())
                .image(cartItemEntity.getProduct().getImages().first().getFileName())
                .quantity(cartItemEntity.getQuantity())
                .build();
    }
}
