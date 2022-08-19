package com.daily.gaboja.cart.controller;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.user.service.UserService;
import com.daily.gaboja.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private CartReadResponseDto cartReadResponseDto;

    @BeforeEach
    void setUp(){
        cartReadResponseDto = new CartReadResponseDto(1L, 1L, null, 100000);
    }

    @Test
    public void get_test_success() throws Exception {
        given(cartService.get(any())).willReturn(cartReadResponseDto);
        mvc.perform(get("/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", notNullValue()));
    }

    @Test
    public void update_test_fail() throws Exception {
        given(cartService.update(any())).willReturn(cartReadResponseDto);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cartId","1");
        mvc.perform(post("/cart/update")
                        .params(params))
                .andExpect(status().isBadRequest());
    }
}