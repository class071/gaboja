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
    @CollectionTable(name = "CART_PRODUCTS", joinColumns = @JoinColumn(referencedColumnName = "id"))
    @Embedded
    private List<CartItem> items;

    @ColumnDefault("0")
    private Integer totalAmounts;

    @Builder
    public Cart(User user) {
        this.user = user;
    }

    public void updateProductLines(List<CartItem> items) {
        this.items = items;
        this.totalAmounts = calculateTotalAmounts(items);
    }

    private Integer calculateTotalAmounts(List<CartItem> lines) {
        return lines.stream()
                .mapToInt(p -> p.getCost() * p.getQuantity()).sum();
    }
}
