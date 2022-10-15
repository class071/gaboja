package com.daily.gaboja.global.advice;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.daily.gaboja.product.constant.ProductConstant.PRODUCT_NOT_EXIST;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchProductExist.class)
    public ApiResponse handleNoSuchProductExist(NoSuchProductExist e) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, PRODUCT_NOT_EXIST);
    }
}