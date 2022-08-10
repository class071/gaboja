package com.daily.gaboja.cart.domain;

import com.daily.gaboja.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    //@CollectionTable(name = "CART_PRODUCTS", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Embedded
    private List<ProductLine> products;

    @ColumnDefault("0")
    private int totalAmounts; // product 연결 후 set 해주는 로직 필요

    @Builder
    public Cart(User user) {
        this.user = user;
    }

    public void changeProductLines(List<ProductLine> newLines) {
        this.products = newLines;
        this.totalAmounts = calculateTotalAmounts(newLines);
    }

    private int calculateTotalAmounts(List<ProductLine> lines) {
        int sum = lines.stream()
                .mapToInt(p -> p.getCost() * p.getQuantity()).sum();
        return sum;
    }
}
