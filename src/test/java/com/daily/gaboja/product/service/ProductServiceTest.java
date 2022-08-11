package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductCreateRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Product Description")
                .stock(10L)
                .category("Test Category")
                .build();

        productResponseDto = new ProductResponseDto().toDto(product);
    }

    @Test
    void 상품_등록_성공() {
        ProductCreateRequestDto productCreateRequestDto = new ProductCreateRequestDto(product.getName(),
                product.getDescription(), product.getStock(), product.getCategory());

        given(productRepository.save(any())).willReturn(product);

        ProductResponseDto response = productService.add(productCreateRequestDto);

        assertThat(response.getName()).isEqualTo(productResponseDto.getName());
    }
}