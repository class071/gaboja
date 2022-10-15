package com.daily.gaboja.product.dto;

import com.daily.gaboja.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    @NotBlank(message = "상품 이름을 입력하시오.")
    private String name;

    @NotBlank(message = "상품 설명을 입력하시오.")
    private String description;

    @PositiveOrZero
    private Long stock;

    @NotBlank(message = "상품 카테고리를 입력하시오.")
    private String category;

    @PositiveOrZero
    private Integer price;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .category(category)
                .price(price)
                .build();
    }
}