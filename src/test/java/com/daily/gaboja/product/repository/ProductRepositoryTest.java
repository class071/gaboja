package com.daily.gaboja.product.repository;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Name")
                .description("Test Description")
                .stock(100L)
                .category("Test Category")
                .price(10000)
                .build();

        productRepository.save(product);
    }

    @Test
    void 상품_ID조회_성공() {
        Product foundProduct = productRepository.findById(1L).orElseThrow(NoSuchProductExist::new);

        assertThat(foundProduct.getId()).isEqualTo(1L);
    }

    @Test
    void 상품_저장_성공() {
        Product foundProduct = productRepository.save(product);

        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
    }
}