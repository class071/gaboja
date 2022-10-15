package com.daily.gaboja.product.exception;

public class NoSuchProductExist extends RuntimeException {

    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    public NoSuchProductExist() {
        super(MESSAGE);
    }
}
