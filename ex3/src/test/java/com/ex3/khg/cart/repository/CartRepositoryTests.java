package com.ex3.khg.cart.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ex3.khg.cart.entity.CartEntity;
import com.ex3.khg.cart.entity.CartItemEntity;
import com.ex3.khg.products.entity.ProductEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CartRepositoryTests {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Test
    @Commit
    public void testInsertCart() {
        String mid = "user22";
        Long pno = 50L;
        int quy = 5;

        Optional<CartEntity> cartResult = cartRepository.findByHolder(mid);

        CartEntity cartEntity = cartResult.orElseGet(() -> {
            CartEntity cart = CartEntity.builder().holder(mid).build();
                
            return cartRepository.save(cart);
        });

        ProductEntity productEntity = ProductEntity.builder().pno(pno).build();

        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .cart(cartEntity)
                .product(productEntity)
                .quantity(quy)
                .build();

        cartItemRepository.save(cartItemEntity);
    }

    // 장바구니 조회 테스트(페치 조인)
    @Test
    public void testRead() {
        String mid = "user22";

        Optional<List<CartItemEntity>> result = cartItemRepository.getCartItemsOfHolder(mid);

        List<CartItemEntity> cartItemEntityList = result.orElse(null);

        cartItemEntityList.forEach(cartItemEntity -> {
            System.out.println(cartItemEntity);
            System.out.println(cartItemEntity.getProduct());
            System.out.println(cartItemEntity.getProduct().getImages());
            System.out.println("--------------------------------------------------");
        });
    }
}
