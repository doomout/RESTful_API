package com.ex3.khg.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.cart.dto.AddCartItemDTO;
import com.ex3.khg.cart.dto.CartItemDTO;
import com.ex3.khg.cart.dto.ModifyCartItemDTO;
import com.ex3.khg.cart.entity.CartEntity;
import com.ex3.khg.cart.entity.CartItemEntity;
import com.ex3.khg.cart.exception.CartTaskException;
import com.ex3.khg.cart.repository.CartItemRepository;
import com.ex3.khg.cart.repository.CartRepository;
import com.ex3.khg.products.entity.ProductEntity;
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

    // 카트 등록
    public void registerItem(AddCartItemDTO addCartItemDTO) {
        String mid = addCartItemDTO.getHolder();
        Long pno = addCartItemDTO.getPno();
        int quantity = addCartItemDTO.getQuantity();

        Optional<CartEntity> cartResult = cartRepository.findByHolder(mid);

        CartEntity cartEntity = cartResult.orElseGet(() -> {
            CartEntity cart = CartEntity.builder().holder(mid).build();

            return cartRepository.save(cart);
        });
        // 상품 정보 조회
        ProductEntity productEntity = productRepository.findById(pno).orElseThrow(CartTaskException.Items.NOT_FOUND_PRODUCT::value);

        // 카트 아이템 생성
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .cart(cartEntity)
                .product(productEntity)
                .quantity(quantity)
                .build();

        try {        
            // 카트 아이템 저장
            cartItemRepository.save(cartItemEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw CartTaskException.Items.CART_ITEM_REGISTER_FAIL.value();
        }
    }

    // 카트 아이템 수정
    public void modifyItem(ModifyCartItemDTO modifyCartItemDTO) {
        Long itemNo = modifyCartItemDTO.getItemNo();
        int quantity = modifyCartItemDTO.getQuantity();

        Optional<CartItemEntity> result = cartItemRepository.findById(itemNo);

        if(result.isEmpty()) {
            throw CartTaskException.Items.NOT_FOUND_CARTITEM.value();
        }

        CartItemEntity cartItemEntity = result.get();

        if(quantity <= 0 ) {
            cartItemRepository.delete(cartItemEntity);
            return;
        }

        cartItemEntity.changeQuantity(quantity);
    }

    // 카트 아이템 소유주 확인
    public void checkItemHolder(String holder, Long itemNo) {
        Optional<String> result = cartItemRepository.getHolderOfCartItem(itemNo);

        if(result.isEmpty()) {
            throw CartTaskException.Items.NOT_FOUND_CARTITEM.value();
        }

        if(!result.get().equals(holder)) {
            throw CartTaskException.Items.NOT_CARTITEM_OWNER.value();
        }
    }
    
    // 카트의 소유주 확인 기능
    public void checkCartHolder(String holder, Long cno) {
        CartEntity cartEntity = cartRepository.findByHolder(holder).orElseThrow(CartTaskException.Items.NOT_FOUND_CART::value);

        if(!cartEntity.getCno().equals(cno)) {
            throw CartTaskException.Items.NOT_FOUND_CART.value();
        }
    }
}
