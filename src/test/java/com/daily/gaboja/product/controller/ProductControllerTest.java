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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
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
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(toJson(productCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value("Test"))
                .andExpect(jsonPath("$.response.stock").value(100L))
                .andDo(document("상품_추가기능",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("추가할 상품 이름"),
                                fieldWithPath("description").description("추가할 상품 설명"),
                                fieldWithPath("stock").description("추가할 상품 재고"),
                                fieldWithPath("category").description("추가할 상품 카테고리"),
                                fieldWithPath("price").description("추가할 상품 가격")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").description("상태 코드"),
                                fieldWithPath("message").description("메시지 - 성공 시 null 반환"),
                                fieldWithPath("response.id").description("상품 ID"),
                                fieldWithPath("response.name").description("상품 이름"),
                                fieldWithPath("response.description").description("상품 설명"),
                                fieldWithPath("response.stock").description("상품 재고"),
                                fieldWithPath("response.category").description("상품 카테고리"),
                                fieldWithPath("response.price").description("상품 가격")
                        )));
    }

    @Test
    void 상품_삭제_성공() throws Exception {
        given(productService.remove(any())).willReturn(productResponseDto.getId());
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/product/{id}", anyLong()))
                .andExpect(status().isOk())
                .andDo(document("상품_삭제기능",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 상품 ID")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").description("상태 코드"),
                                fieldWithPath("message").description("메시지 - 성공 시 null 반환"),
                                fieldWithPath("response").description("삭제된 유저 ID")
                        )));
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