package com.daily.gaboja.user.service;

import com.daily.gaboja.user.dto.LoginResponse;

public interface UserService {

    String getCode();
    LoginResponse getAccessToken(String code);
    LoginResponse loginWithAccessToken(String token);
    String changeUserRole(long id);
}
