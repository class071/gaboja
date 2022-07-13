package com.daily.gaboja.user.dto;

import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NaverProfile {

    String name;
    String email;
    String profile;
    String gender;
    String age;
    UserRole userRole;

    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .profile(profile)
                .gender(gender)
                .age(age)
                .role(userRole)
                .build();
    }
}
