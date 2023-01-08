package com.daily.gaboja.order.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.order.dto.ChangeInfoReqDto;
import com.daily.gaboja.order.dto.OrderRequestDto;
import com.daily.gaboja.order.dto.OrderResponseDto;
import com.daily.gaboja.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/info")
    public ApiResponse<OrderResponseDto> changeInfo(@RequestBody @Valid ChangeInfoReqDto changeInfoReqDto){
        return ApiResponse.success(HttpStatus.OK, orderService.changeShippingInfo(changeInfoReqDto));
    }

    @PostMapping("/order")
    public ApiResponse<OrderResponseDto> create(@RequestBody @Valid OrderRequestDto orderRequestDto){
        return ApiResponse.success(HttpStatus.OK, orderService.sendCartToOrder(orderRequestDto));
    }
}