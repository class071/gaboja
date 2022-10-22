package com.daily.gaboja.product.controller;

import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.jwt.config.CustomUserDetailsService;
import com.daily.gaboja.product.dto.ProductRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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
    void 상품_추가_성공() throws Exception {
        given(productService.add(any())).willReturn(productResponseDto);
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(toJson(productCreateRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.name").value("Test"))
                .andExpect(jsonPath("$.response.stock").value(100L));
    }

    @Test
    void 상품_조회_성공() throws Exception {
        given(productService.readOne(any())).willReturn(productResponseDto);
        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value("Test"));
    }

    @Test
    void 상품_삭제_성공() throws Exception {
        given(productService.remove(any())).willReturn(productResponseDto.getId());
        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(1));
    }

    @Test
    void 상품_전체조회_성공() throws Exception {
        given(productService.readAll()).willReturn(Arrays.asList(productResponseDto));
        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").isArray());
    }

    @Test
    void 상품_수정_성공() throws Exception {
        ProductResponseDto responseDto = new ProductResponseDto(1L, "Test1", "Test1", 200L, "Test1", 2000);

        given(productService.update(any(), any())).willReturn(responseDto);
        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(toJson(productUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value("Test1"))
                .andExpect(jsonPath("$.response.stock").value(200L));
    }

    public static String toJson(Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}