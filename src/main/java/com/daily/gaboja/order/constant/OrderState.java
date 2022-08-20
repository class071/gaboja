package com.daily.gaboja.order.constant;

public enum OrderState {

    // 결제 대기, 결제 완료, 배송 중, 배송 완료,
    // 결제 대기 에만 주문 수정, 취소 가능
    // 결제 완료 전에만 배송지 shippingInfo 수정 가능
    // 배송중 ~ 배송완료 상태의 Order는 어떠한 기능 불가

    PAYMENT_WAIT, PAYMENT_FINISH, DELIVERY_ING, DELIVERY_FINISH;
}
