package com.daily.gaboja.product.domain;

import com.daily.gaboja.product.dto.ProductUpdateRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void 수정_성공() {
        Product product = Product.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .stock(100L)
                .category("Test")
                .price(1000)
                .build();

        ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto(
                "Test2", "Test2", 100L, "Test2", 1000);

        product.update(productUpdateRequestDto);

        assertThat(product.getName()).isEqualTo(productUpdateRequestDto.getName());
    }
}