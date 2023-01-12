package com.daily.gaboja.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import com.daily.gaboja.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductQueryServiceTest {

    @InjectMocks
    private ProductQueryService productQueryService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_조회_성공() {
        Product product = Product.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .stock(100L)
                .category("Test")
                .price(1000)
                .build();

        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));

        ProductResponseDto foundProduct = productQueryService.readOne(product.getId());

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
    }

    @Test
    void 상품_조회_실패() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productQueryService.readOne(1L)).isInstanceOf(NoSuchProductExist.class);
    }

    @Test
    void 상품_전체조회_성공() {
        Product product1 = Product.builder()
                .id(2L)
                .name("Test2")
                .description("Test2")
                .stock(100L)
                .category("Test2")
                .price(1000)
                .build();

        Product product2 = Product.builder()
                .id(3L)
                .name("Test3")
                .description("Test3")
                .stock(100L)
                .category("Test3")
                .price(1000)
                .build();

        List<Product> foundList = new ArrayList<>();
        foundList.add(product1);
        foundList.add(product2);

        given(productRepository.findAll()).willReturn(foundList);

        List<ProductResponseDto> productResponseDtoList = productQueryService.readAll();

        assertThat(productResponseDtoList.size()).isEqualTo(2);
    }

}
