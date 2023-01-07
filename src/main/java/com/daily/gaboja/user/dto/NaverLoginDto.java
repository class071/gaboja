package com.daily.gaboja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverLoginDto {

    String email;
    String name;
    String profile;
    String gender;
    String age;
}
