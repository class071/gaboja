package com.daily.gaboja.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ColumnDefault("0")
    private Long stock;

    @Column(nullable = false)
    private String category;

    @Builder
    public Product(Long id, String name, String description, Long stock, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }

    public void update(String name, String description, Long stock, String category) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }
}
