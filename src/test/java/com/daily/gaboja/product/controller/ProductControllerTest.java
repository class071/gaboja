package com.daily.gaboja.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.daily.gaboja.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Mock
    MockMvc mockMvc;

    @Test
    void 테스트() throws Exception {
        mockMvc.perform((get("/api/product")))
                .andExpect(status().isOk());
    }
}