package com.daily.gaboja.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    CUSTOMER("C"),
    SELLER("S");

    private final String type;
}
