package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.domain.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartReadResponseDto {

    private Long cartId;

    private Long userId;

    private List<ProductLine> productLines;

    private int totalAmounts;

    public CartReadResponseDto toDto(Cart cart) {
        this.cartId = cart.getId();
        this.userId = cart.getUser().getId();
        this.productLines = cart.getProducts();
        this.totalAmounts = cart.getTotalAmounts();

        return this;
    }
}
