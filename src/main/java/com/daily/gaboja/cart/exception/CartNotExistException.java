package com.daily.gaboja.cart.exception;

public class CartNotExistException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 장바구니입니다.";

    public CartNotExistException() {
        super(MESSAGE);
    }
}
