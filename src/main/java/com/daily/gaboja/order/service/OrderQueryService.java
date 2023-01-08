package com.daily.gaboja.order.service;

import com.daily.gaboja.order.domain.Order;
import com.daily.gaboja.order.dto.OrderResponseDto;
import com.daily.gaboja.order.exception.OrderNotExistException;
import com.daily.gaboja.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;


    public OrderResponseDto get(long orderId) {
        Order order = findById(orderId);
        return toDto(order);
    }

    private OrderResponseDto toDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        return orderResponseDto.toDto(order);
    }

    private Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotExistException::new);
    }
}
