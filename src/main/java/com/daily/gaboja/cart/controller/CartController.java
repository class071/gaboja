package com.daily.gaboja.cart.controller;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{userId}")
    public ApiResponse<Long> register(@PathVariable Long userId) {
        return ApiResponse.success(HttpStatus.OK, cartService.create(userId));
    }

    @DeleteMapping("/cart/{userId}")
    public ApiResponse<Long> delete(@PathVariable Long userId) {
        cartService.delete(userId);
        return ApiResponse.success(HttpStatus.OK, userId);
    }

    @GetMapping("/cart/get/{userId}")
    public ApiResponse<CartReadResponseDto> get(@PathVariable Long userId){
        return ApiResponse.success(HttpStatus.OK, cartService.get(userId));
    }
}
