package com.daily.gaboja.user.domain;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.user.constant.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TABLE_USER")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String profile;

    private String age;

    private String gender;

    private String email;

    @Column(name = "USER_ROLE")
    private UserRole role;

    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Cart cart;

    @Builder
    public User(String name, String email, String profile, String age, String gender, UserRole role) {
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.age = age;
        this.gender = gender;
        this.role = role;
    }

    public void grantSellerRole() {
        this.role = UserRole.SELLER;
    }

    public void setCart(Cart cart){
        this.cart = cart;
    }
}
