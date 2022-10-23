package com.daily.gaboja.global.advice;

import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.exception.WrongUserIdException;
import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.order.exception.OrderNotExistException;
import com.daily.gaboja.user.exception.ParseFailedException;
import com.daily.gaboja.user.exception.UserNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CartNotExistException.class)
    public ApiResponse handleCartNotExistException(CartNotExistException e) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(WrongUserIdException.class)
    public ApiResponse handleWrongUserIdException(WrongUserIdException e) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(OrderNotExistException.class)
    public ApiResponse handleOrderNotExistException(OrderNotExistException e) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ParseFailedException.class)
    public ApiResponse handleParseFailedException(ParseFailedException e) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UserNotExistException.class)
    public ApiResponse handleUserNotExistException(UserNotExistException e) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}