package com.daily.gaboja.product.domain;

import com.daily.gaboja.product.dto.ProductRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    @ColumnDefault("0")
    private long stock;

    @Column(nullable = false)
    private String category;

    @Column
    @ColumnDefault("0")
    private int price;

    public void update(ProductRequestDto productRequestDto) {
        this.name = productRequestDto.getName();
        this.description = productRequestDto.getDescription();
        this.stock = productRequestDto.getStock();
        this.category = productRequestDto.getCategory();
        this.price = productRequestDto.getPrice();
    }
}