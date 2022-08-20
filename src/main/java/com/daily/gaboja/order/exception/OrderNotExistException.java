
package com.daily.gaboja.order.exception;

public class OrderNotExistException extends RuntimeException {

    private static final String MESSAGE = "해당 주문은 존재하지 않습니다.";

    public OrderNotExistException() {
        super(MESSAGE);
    }
}