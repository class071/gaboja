package com.daily.gaboja.user.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.user.dto.LoginResponse;
import com.daily.gaboja.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/login/naver")
    public String login(){
        return userService.getCode();
    }

    @GetMapping("/api/login/naver/callback")
    public ApiResponse<LoginResponse> callback_naver(@RequestParam String code){
        return ApiResponse.success(HttpStatus.OK, userService.getAccessToken(code));
    }

}