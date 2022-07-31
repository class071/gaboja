package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CartRegisterDto {

    @NotNull
    private Long userId;

    public CartRegisterDto(Long userId) {
        this.userId = userId;
    }

    public Cart toEntity(User user) {
        return Cart.builder()
                .user(user)
                .build();
    }
}
