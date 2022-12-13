package com.daily.gaboja.cart.dto;

import com.daily.gaboja.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDto {

    @NotNull
    private Long cartId;

    @Nullable
    private List<CartItem> cartItems;
}
