package com.daily.gaboja.product.domain;

import com.daily.gaboja.product.dto.ProductUpdateRequestDto;
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

    public void update(ProductUpdateRequestDto productUpdateRequestDto) {
        this.name = productUpdateRequestDto.getName();
        this.description = productUpdateRequestDto.getDescription();
        this.stock = productUpdateRequestDto.getStock();
        this.category = productUpdateRequestDto.getCategory();
    }
}
