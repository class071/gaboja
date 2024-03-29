package com.daily.gaboja.product.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.product.dto.ProductRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.dto.ProductSearchRequestDto;
import com.daily.gaboja.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/product")
    public ApiResponse<ProductResponseDto> add(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.add(productRequestDto);
        return ApiResponse.success(HttpStatus.CREATED, productResponseDto);
    }

    @PutMapping("/api/product/{id}")
    public ApiResponse<ProductResponseDto> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.update(id, productRequestDto);
        return ApiResponse.success(HttpStatus.OK, productResponseDto);
    }

    @DeleteMapping("/api/product/{id}")
    public ApiResponse<ProductResponseDto> remove(@PathVariable Long id) {
        productService.remove(id);
        return ApiResponse.success(HttpStatus.OK, id);
    }
}