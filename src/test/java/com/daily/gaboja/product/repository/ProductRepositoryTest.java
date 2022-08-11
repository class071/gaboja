package com.daily.gaboja.product.repository;

import com.daily.gaboja.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addProduct() {
        Product newProduct = new Product(1L, "Test Name", "Test Description", 100L, "Test Category");

        Product addProduct = productRepository.save(newProduct);

        Assertions.assertThat(newProduct).isSameAs(addProduct);
        Assertions.assertThat(newProduct.getName()).isEqualTo(addProduct.getName());
        Assertions.assertThat(productRepository.count()).isEqualTo(1);
    }
}