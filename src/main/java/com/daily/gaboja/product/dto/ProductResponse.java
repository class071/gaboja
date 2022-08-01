package com.daily.gaboja.product.dto;

import com.daily.gaboja.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private Long stock;

    private String category;

    public ProductResponse toDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.stock = product.getStock();
        this.category = product.getCategory();

        return this;
    }
}
