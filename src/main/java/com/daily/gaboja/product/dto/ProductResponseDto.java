package com.daily.gaboja.product.dto;

import com.daily.gaboja.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private Long stock;

    private String category;

    private Integer price;

    public ProductResponseDto toDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.stock = product.getStock();
        this.category = product.getCategory();
        this.price = product.getPrice();

        return this;
    }
}