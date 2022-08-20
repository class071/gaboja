package com.daily.gaboja.order.domain;

import com.daily.gaboja.order.constant.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "ORDER_TABLE")
@Getter
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderState orderState;

    private Long cartId;

    @Embedded
    private ShippingInfo shippingInfo;

    private int totalAmount;

    public void changeShippingInfo(ShippingInfo newInfo){
        this.shippingInfo = newInfo;
    }
}