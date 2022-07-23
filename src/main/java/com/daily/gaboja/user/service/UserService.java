package com.daily.gaboja.user.service;

import com.daily.gaboja.user.dto.LoginResponse;

public interface UserService {

    String getLoginCode();
    LoginResponse getAccessToken(String code);
    LoginResponse loginWithAccessToken(String token);
    String requestSellerRole(String b_no, long id);
}
