package com.daily.gaboja.cart.service;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.exception.UserNotExistException;
import com.daily.gaboja.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.elasticsearch.common.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.any;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
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
    public void 장바구니_생성_성공(){
        User user = User.builder()
                .age("22")
                .email("yyy306@naver.com")
                .gender("man")
                .name("gnoesnooj2")
                .profile("profile2")
                .role(UserRole.CUSTOMER)
                .build();
        userRepository.save(user); // 연관관계로 cart가 생성된다.
        Assertions.assertThat(cartRepository.findById(2L)).isNotNull();
    }

    @Test
    public void 장바구니_생성_실패(){ // 유저가 없기 때문에 실패해야함
        // Assertions.assertThat(cartService.create(2L)).isEqualTo(2L);

        org.junit.jupiter.api.Assertions.assertThrows(UserNotExistException.class, () -> {
            cartService.create(2L);
        } );
    }
}
