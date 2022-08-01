package com.daily.gaboja.product.repository;

import com.daily.gaboja.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
