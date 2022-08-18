package com.daily.gaboja.cart.repository;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    private Cart cart;

    @BeforeEach
    void setUp(){
        User user = User.builder()
                .age("22")
                .email("yyy306@naver.com")
                .gender("man")
                .name("gnoesnooj")
                .profile("profile")
                .role(UserRole.CUSTOMER)
                .build();

        userRepository.save(user);

        cart = Cart.builder()
                .user(user)
                .build();

        cartRepository.save(cart);
    }

    @Test
    void 장바구니_ID조회_성공(){
        Cart cart = cartRepository.findById(1L).orElseThrow(CartNotExistException::new);

        Assertions.assertThat(cart.getId()).isEqualTo(1L);
    }

    @Test
    void 장바구니_조회_실패(){
        Cart cart = cartRepository.findById(1L).orElseThrow(CartNotExistException::new);

        org.junit.jupiter.api.Assertions.assertThrows(CartNotExistException.class, () -> {
            cartRepository.findById(5L).orElseThrow(CartNotExistException::new);
        } );
    }

    @Test
    void 장바구니_저장_성공(){
        given(cartRepository.save(cart)).willReturn(cart);

        Assertions.assertThat(cartRepository.save(cart)).isEqualTo(cart);
    }
}
