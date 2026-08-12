package com.ex3.khg.cart.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CartTaskException extends RuntimeException {
    private String message;
    private int status;

    public CartTaskException(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public static enum Items {
        NOT_FOUND_CARTITEM("장바구니 상품이 존재하지 않습니다.", 404),
        NOT_FOUND_CART("장바구니가 존재하지 않습니다.", 404),
        NOT_FOUND_PRODUCT("상품이 존재하지 않습니다.", 404),
        INVALID_QUANTITY("유효하지 않은 수량입니다.", 400),
        DUPLICATE_PRODUCT("이미 장바구니에 존재하는 상품입니다.", 400),
        CART_ITEM_REGISTER_FAIL("장바구니 상품 등록에 실패했습니다.", 500),
        CART_ITEM_UPDATE_FAIL("장바구니 상품 수량 변경에 실패했습니다.", 500),
        CART_ITEM_DELETE_FAIL("장바구니 상품 삭제에 실패했습니다.", 500);

        private String message;
        private int status;

        Items(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public CartTaskException value() {
            return new CartTaskException(this.message, this.status);
        }
    }
}
