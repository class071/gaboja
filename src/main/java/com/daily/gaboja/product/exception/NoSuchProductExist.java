package com.daily.gaboja.product.exception;

public class NoSuchProductExist extends RuntimeException {

    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    private static final String MESSAGE_WITH_ID = "상품번호 : ";

    private static final String NOT_EXIST = "(이)가 존재하지 않습니다.";

    public NoSuchProductExist() {
        super(MESSAGE);
    }

    public NoSuchProductExist(Long id) {
        super(MESSAGE_WITH_ID + id + NOT_EXIST);
    }
}