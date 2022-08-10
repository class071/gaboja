package com.daily.gaboja.cart.controller;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.dto.CartUpdateRequestDto;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/{userId}")
    public ApiResponse<Long> register(@PathVariable @Valid Long userId) {
        return ApiResponse.success(HttpStatus.OK, cartService.create(userId));
    }

    @DeleteMapping("/cart/{userId}")
    public ApiResponse<Long> delete(@PathVariable @Valid Long userId) {
        cartService.delete(userId);
        return ApiResponse.success(HttpStatus.OK, userId);
    }

    @GetMapping("/cart/{userId}")
    public ApiResponse<CartReadResponseDto> get(@PathVariable Long userId){
        return ApiResponse.success(HttpStatus.OK, cartService.get(userId));
    }

    @PostMapping("/cart/update")
    public ApiResponse<CartReadResponseDto> update(@RequestBody @Valid CartUpdateRequestDto cartUpdateRequestDto){ // request: cartId, List<ProductLine>
        return ApiResponse.success(HttpStatus.OK, cartService.update(cartUpdateRequestDto));
    }
}
