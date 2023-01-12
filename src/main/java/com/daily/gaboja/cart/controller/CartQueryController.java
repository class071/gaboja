package com.daily.gaboja.cart.controller;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.service.CartQueryService;
import com.daily.gaboja.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryController {

    private final CartQueryService cartQueryService;

    @GetMapping("/cart/{userId}")
    public ApiResponse<CartReadResponseDto> get(@PathVariable Long userId) {
        return ApiResponse.success(HttpStatus.OK, cartQueryService.get(userId));
    }

}
