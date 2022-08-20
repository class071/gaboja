
package com.daily.gaboja.order.dto;

import com.daily.gaboja.order.constant.OrderState;
import com.daily.gaboja.order.domain.Address;
import com.daily.gaboja.order.domain.Order;
import com.daily.gaboja.order.domain.ShippingInfo;
import lombok.Getter;

import javax.persistence.Embedded;

@Getter
public class OrderResponseDto {

    @Embedded
    private ShippingInfo shippingInfo;

    private OrderState orderState;

    private int totalAmount;

    public OrderResponseDto toDto(Order order) {
        this.shippingInfo = order.getShippingInfo();
        this.totalAmount = order.getTotalAmount();
        this.orderState = order.getOrderState();

        return this;
    }
}
