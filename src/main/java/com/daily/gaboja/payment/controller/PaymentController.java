
package com.daily.gaboja.payment.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.payment.dto.response.ApproveResponse;
import com.daily.gaboja.payment.dto.response.ReadyResponse;
import com.daily.gaboja.payment.service.PaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payment/ready/{orderId}")
    public ApiResponse<ReadyResponse> ready(@PathVariable Long orderId){ // cart id, 주문자정보
        ReadyResponse readyResponse = paymentService.ready(orderId);
        return ApiResponse.success(HttpStatus.OK, readyResponse);
    }

    @GetMapping("/api/payment/approve")
    public ApiResponse<ApproveResponse> approval_url(@RequestParam("pg_token") String pgToken, @RequestParam("orderId") Long orderId){
        return ApiResponse.success(HttpStatus.OK, paymentService.approve(orderId, pgToken));
    }

    @GetMapping("/api/payment/cancel")
    public ApiResponse cancel_url(){
        return ApiResponse.error(HttpStatus.OK, "PAYMENT_CANCEL");
    }

    @GetMapping("/api/payment/fail")
    public ApiResponse fail_url(){
        return ApiResponse.error(HttpStatus.OK, "PAYMENT_FAIL");
    }
}

