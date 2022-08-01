package com.daily.gaboja.product.exception;

public class ProductNotExist extends RuntimeException {

    private static final String message = "상품이 존재하지 않습니다.";

    public ProductNotExist() {
        super(message);
    }
}
