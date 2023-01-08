package com.daily.gaboja.cart;

import com.daily.gaboja.cart.controller.CartController;
import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.dto.CartUpdateRequestDto;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

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
    void setUp(){
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
        // TODO : cqrs 분리에 따른 테스트 코드 수정 필요
        //given(cartService.get(any())).willReturn(cartReadResponseDto);
        mvc.perform(get("/cart/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", notNullValue()))
                .andDo(document("장바구니_조회기능"));
    }

    @Test
    public void update_test_success() throws Exception {
        CartUpdateRequestDto cartUpdateRequestDto = new CartUpdateRequestDto(1L, null);
        given(cartService.update(any())).willReturn(cartReadResponseDto);
        mvc.perform(RestDocumentationRequestBuilders.post("/cart/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(cartUpdateRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(document("장바구니_수정기능",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cartId").description("수정할 장바구니 ID"),
                                fieldWithPath("cartItems").description("수정할 장바구니 상품목록")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").description("상태 코드"),
                                fieldWithPath("message").description("메세지 - 성공 시 null로 반환"),
                                fieldWithPath("response").description("응답 객체"),
                                fieldWithPath("response.cartId").description("장바구니 ID"),
                                fieldWithPath("response.userId").description("회원 ID"),
                                fieldWithPath("response.cartItems").description("상품 목록"),
                                fieldWithPath("response.totalAmounts").description("총액")
                        )));
    }

    public static String toJson(Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}