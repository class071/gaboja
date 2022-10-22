package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import com.daily.gaboja.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_추가_성공() {
        Product product = Product.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .stock(100L)
                .category("Test")
                .price(1000)
                .build();

        ProductRequestDto dto = new ProductRequestDto("Test", "Test", 100L, "Test", 1000);

        given(productRepository.save(any())).willReturn(product);

        ProductResponseDto newProduct = productService.add(dto);

        assertThat(newProduct).isNotNull();
        assertThat(newProduct.getId()).isEqualTo(product.getId());
        assertThat(newProduct.getName()).isEqualTo(product.getName());
        assertThat(newProduct.getDescription()).isEqualTo(product.getDescription());
    }

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

        ProductResponseDto foundProduct = productService.readOne(product.getId());

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
    }

    @Test
    void 상품_조회_실패() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.readOne(1L)).isInstanceOf(NoSuchProductExist.class);
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

        List<ProductResponseDto> productResponseDtoList = productService.readAll();

        assertThat(productResponseDtoList.size()).isEqualTo(2);
    }

    @Test
    void 상품_삭제_성공() {
        Product product1 = Product.builder()
                .id(2L)
                .name("Test2")
                .description("Test2")
                .stock(100L)
                .category("Test2")
                .price(1000)
                .build();

        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product1));

        productService.remove(product1.getId());

        verify(productRepository).delete(any());
    }

    @Test
    void 상품_삭제_실패() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.remove(1L)).isInstanceOf(NoSuchProductExist.class);
    }

    @Test
    void 상품_수정_성공() {
        Product product1 = Product.builder()
                .id(2L)
                .name("Test2")
                .description("Test2")
                .stock(100L)
                .category("Test2")
                .price(1000)
                .build();

        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product1));

        ProductRequestDto productUpdateRequestDto = new ProductRequestDto(
                "Test5", "Test5", 100L, "Test5", 1000);

        productService.update(product1.getId(), productUpdateRequestDto);

        assertThat(product1.getName()).isEqualTo(productUpdateRequestDto.getName());
        assertThat(product1.getDescription()).isEqualTo(productUpdateRequestDto.getDescription());
    }

    @Test
    void 상품_수정_실패() {
        ProductRequestDto productUpdateRequestDto = new ProductRequestDto(
                "Test2", "Test2", 100L, "Test", 1000);

        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(1L, productUpdateRequestDto)).isInstanceOf(NoSuchProductExist.class);
    }
}