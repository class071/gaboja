package com.daily.gaboja.order.dto;

import com.daily.gaboja.order.domain.ShippingInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NotNull
@Getter
@NoArgsConstructor
public class ChangeInfoReqDto {

    private Long orderId;

    private ShippingInfo shippingInfo;
}
