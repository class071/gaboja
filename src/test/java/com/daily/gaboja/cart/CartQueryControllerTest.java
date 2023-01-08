package com.daily.gaboja.cart;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.daily.gaboja.cart.controller.CartController;
import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.cart.service.CartQueryService;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@AutoConfigureRestDocs
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(CartController.class)
public class CartQueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartQueryService cartQueryService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private TokenService tokenService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    private CartReadResponseDto cartReadResponseDto;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cartReadResponseDto = new CartReadResponseDto();
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
    public void get_test_success() throws Exception {
        given(cartQueryService.get(any())).willReturn(cartReadResponseDto);
        mvc.perform(get("/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", notNullValue()))
                .andDo(document("장바구니_조회기능"));
    }

}
