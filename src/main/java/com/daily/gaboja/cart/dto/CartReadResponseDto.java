package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.Cart;
import lombok.Getter;

@Getter
public class CartReadResponseDto {

    private Long cartId;

    private Long userId;

    public CartReadResponseDto convertEntityToDto(Cart cart) {
        this.cartId = cart.getId();
        this.userId = cart.getUser().getId();

        return this;
    }
}
