package com.daily.gaboja.order.dto;

import com.daily.gaboja.order.domain.ShippingInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NotNull
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Long cartId;

    private ShippingInfo shippingInfo;
}
