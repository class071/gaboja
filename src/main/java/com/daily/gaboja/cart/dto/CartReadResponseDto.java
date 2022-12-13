package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartReadResponseDto {

    private Long cartId;

    private Long userId;

    private List<CartItem> cartItems;

    private Integer totalAmounts;

    public CartReadResponseDto toDto(Cart cart) {
        this.cartId = cart.getId();
        this.userId = cart.getUser().getId();
        this.cartItems = cart.getItems();
        this.totalAmounts = cart.getTotalAmounts();

        return this;
    }
}
