package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.domain.ProductLine;
import lombok.Getter;

import java.util.List;

@Getter
public final class CartReadResponseDto {

    private static Long cartId;

    private static Long userId;

    private static List<ProductLine> productLines;

    private static int totalAmounts;

    public static CartReadResponseDto toDto(Cart cart) {
        CartReadResponseDto cartReadResponseDto = new CartReadResponseDto();
        cartId = cart.getId();
        userId = cart.getUser().getId();
        productLines = cart.getProducts();
        totalAmounts = cart.getTotalAmounts();

        return cartReadResponseDto;
    }
}
