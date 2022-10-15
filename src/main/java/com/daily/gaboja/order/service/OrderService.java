
package com.daily.gaboja.order.service;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.order.constant.OrderState;
import com.daily.gaboja.order.domain.Order;
import com.daily.gaboja.order.dto.ChangeInfoReqDto;
import com.daily.gaboja.order.dto.OrderRequestDto;
import com.daily.gaboja.order.dto.OrderResponseDto;
import com.daily.gaboja.order.exception.OrderNotExistException;
import com.daily.gaboja.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    public OrderResponseDto get(long orderId) {
        Order order = findById(orderId);
        return toDto(order);
    }

    public OrderResponseDto changeShippingInfo(ChangeInfoReqDto changeInfoReqDto) { // DTO : orderID, ShippingInfo
        Order order = findById(changeInfoReqDto.getOrderId());
        order.changeShippingInfo(changeInfoReqDto.getShippingInfo());
        return toDto(order);
    }

    public OrderResponseDto sendCartToOrder(OrderRequestDto orderRequestDto) {
        Order order = buildOrder(orderRequestDto);
        orderRepository.save(order);
        return toDto(order);
    }

    private Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotExistException::new);
    }

    private OrderResponseDto toDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        return orderResponseDto.toDto(order);
    }

    private Order buildOrder(OrderRequestDto orderRequestDto) {
        Cart cart = cartRepository.findById(orderRequestDto.getCartId()).orElseThrow(CartNotExistException::new);
        int totalAmount = cart.getTotalAmounts();
        Order order = Order.builder()
                .cartId(cart.getId())
                .shippingInfo(orderRequestDto.getShippingInfo())
                .totalAmount(totalAmount)
                .orderState(OrderState.PAYMENT_WAIT)
                .build();

        return order;
    }
}
