package com.daily.gaboja.order.domain;

import com.daily.gaboja.order.constant.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.elasticsearch.cluster.metadata.AliasAction;

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

    private Long cartId; // 연관관계를 통한 cart 접근 필요 // cart 에 ProductLine 필요 // value 클래스 ProductLine 필요

    @Embedded
    private ShippingInfo shippingInfo;

    private int totalAmount;

    public void changeShippingInfo(ShippingInfo newInfo){
        this.shippingInfo = newInfo;
    }
}