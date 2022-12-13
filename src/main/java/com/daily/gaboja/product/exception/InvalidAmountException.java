package com.daily.gaboja.product.exception;

public class InvalidAmountException extends RuntimeException{

    private static final String CHOOSE_PRODUCT = "선택하신 상품명 : ";

    private static final String NOT_EXIST = "의 수량이 너무 많습니다.";

    public InvalidAmountException(String name){
        super(CHOOSE_PRODUCT + name + NOT_EXIST);
    }
}
