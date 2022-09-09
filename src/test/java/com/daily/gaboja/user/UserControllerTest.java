package com.daily.gaboja.user;

import com.daily.gaboja.cart.controller.CartController;
import com.daily.gaboja.cart.service.CartService;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.controller.UserController;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.dto.LoginResponse;
import com.daily.gaboja.user.dto.NaverProfile;
import com.daily.gaboja.user.repository.UserRepository;
import com.daily.gaboja.user.service.UserService;
import com.daily.gaboja.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.daily.gaboja.cart.CartControllerTest.toJson;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .role(UserRole.CUSTOMER)
                .age("20")
                .email("306yyy@naver.com")
                .profile("profile")
                .name("name")
                .gender("man")
                .build();

        userRepository.save(user);
    }

    @Test
    public void get_LoginCode_success() throws Exception {
        given(userServiceImpl.getLoginCode()).willReturn("LOGIN_CODE");
        mvc.perform(get("/api/login/naver"))
                .andExpect(status().isOk())
                .andDo(document("유저_로그인_인가코드받기"));
    }

    @Test
    public void callback_success() throws Exception {
        LoginResponse loginResponse = new LoginResponse("ACCESS_TOKEN", "REFRESH_TOKEN");
        given(userServiceImpl.getAccessToken(any())).willReturn(loginResponse);
        mvc.perform(RestDocumentationRequestBuilders.get("/api/login/naver/callback")
                        .param("code", "LOGIN_CODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("유저_로그인_네이버콜백",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(parameterWithName("code").description("네이버 인가코드")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").description("상태 코드"),
                                fieldWithPath("message").description("메세지 - 성공 시 null로 반환"),
                                fieldWithPath("response").description("응답"),
                                fieldWithPath("response.accessToken").description("액세스 토큰"),
                                fieldWithPath("response.refreshToken").description("리프래시 토큰")
                        )
                ));
    }

    @Test
    public void delete_test() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders.delete("/api/user/{id}", anyLong()))
                .andExpect(status().isOk())
                .andDo(document("유저_삭제",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 유저 ID")
                        )
                ));
    }

    @Test
    public void covert_test() throws Exception {
        given(userServiceImpl.requestSellerRole(anyLong())).willReturn("USER_ROLE");
        mvc.perform(RestDocumentationRequestBuilders.get("/api/user/convert", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id","1"))
                .andExpect(status().isOk())
                .andDo(document("유저_회원권한수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("id").description("권한을 수정해줄 유저 ID")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").description("상태 코드"),
                                fieldWithPath("message").description("메세지 - 성공 시 null로 반환"),
                                fieldWithPath("response").description("수정된 회원의 권한")
                        )
                ));
    }
}