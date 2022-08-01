package com.daily.gaboja.product.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.product.dto.ProductCreateRequest;
import com.daily.gaboja.product.dto.ProductResponse;
import com.daily.gaboja.product.dto.ProductUpdateRequest;
import com.daily.gaboja.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product")
    public ApiResponse<ProductResponse> add(@RequestBody ProductCreateRequest productCreateRequest) {
        ProductResponse productResponse = productService.add(productCreateRequest);
        return ApiResponse.success(HttpStatus.CREATED, productResponse);
    }

    @PutMapping("/api/product/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        ProductResponse productResponse = productService.update(id, productUpdateRequest);
        return ApiResponse.success(HttpStatus.OK, productResponse);
    }

    @DeleteMapping("/api/product/{id}")
    public ApiResponse<ProductResponse> remove(@PathVariable Long id) {
        productService.remove(id);
        return ApiResponse.success(HttpStatus.OK, id);
    }

    @GetMapping("/api/product/{id}")
    public ApiResponse<ProductResponse> readOne(@PathVariable Long id) {
        ProductResponse productResponse = productService.readOne(id);
        return ApiResponse.success(HttpStatus.OK, productResponse);
    }

    @GetMapping("/api/product")
    public ApiResponse<List<ProductResponse>> readAll() {
        List<ProductResponse> productResponses = productService.readAll();
        return ApiResponse.success(HttpStatus.OK, productResponses);
    }
}
