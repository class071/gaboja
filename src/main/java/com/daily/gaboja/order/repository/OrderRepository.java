
package com.daily.gaboja.order.repository;

import com.daily.gaboja.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order save(Order order);

    Optional<Order> findById(Long orderId);
}
