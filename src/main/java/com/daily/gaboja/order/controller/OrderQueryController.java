package com.daily.gaboja.order.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.order.dto.OrderResponseDto;
import com.daily.gaboja.order.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    @GetMapping("/order/{orderId}")
    public ApiResponse<OrderResponseDto> get(@PathVariable Long orderId) {
        return ApiResponse.success(HttpStatus.OK, orderQueryService.get(orderId));
    }
}
