package com.daily.gaboja.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.product.dto.ProductRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.service.ProductQueryService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(ProductQueryController.class)
public class ProductQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductQueryService productQueryService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    private ProductResponseDto productResponseDto;

    private ProductRequestDto productCreateRequestDto;

    private ProductRequestDto productUpdateRequestDto;


    @BeforeEach
    void setUp() {
        productResponseDto = new ProductResponseDto(1L, "Test", "Test", 100L, "Test", 1000);
        productCreateRequestDto = new ProductRequestDto("Test", "Test", 100L, "Test", 1000);
        productUpdateRequestDto = new ProductRequestDto("Test1", "Test1", 200L, "Test1", 2000);
    }

    @Test
    void 상품_조회_성공() throws Exception {
        given(productQueryService.readOne(any())).willReturn(productResponseDto);
        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value("Test"))
                .andDo(document("상품 조회기능"));
    }

    @Test
    void 상품_전체조회_성공() throws Exception {
        given(productQueryService.readAll()).willReturn(Arrays.asList(productResponseDto));
        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").isArray())
                .andDo(document("상품_전체조회기능"));
    }
}
