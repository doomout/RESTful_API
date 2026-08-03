package com.ex3.khg.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex3.khg.cart.entity.CartItemEntity;
import java.util.List;
import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    // 페치 조인을 이용한 장바구니 아이템 조회 메서드
    @Query("select c from CartItemEntity c" +
           " join fetch c.product " +
           " join fetch c.product.images " +
           " where c.cart.holder = :holder " +
           " order by c.itemNo desc")
    Optional<List<CartItemEntity>> getCartItemsOfHolder(@Param("holder") String holder);
}
