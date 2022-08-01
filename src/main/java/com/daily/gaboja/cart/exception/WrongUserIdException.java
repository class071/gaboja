package com.daily.gaboja.cart.exception;

public class WrongUserIdException extends IllegalArgumentException {
    private static final String MESSAGE = "잘못된 유저 아이디거나 잘못된 아이디 요청입니다.";

    public WrongUserIdException() {
        super(MESSAGE);
    }
}
