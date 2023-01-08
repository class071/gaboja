package com.daily.gaboja.product.controller;

import com.daily.gaboja.global.api.ApiResponse;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.dto.ProductSearchRequestDto;
import com.daily.gaboja.product.service.ProductQueryService;
import com.daily.gaboja.product.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping("/api/product/{id}")
    public ApiResponse<ProductResponseDto> readOne(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productQueryService.readOne(id);
        return ApiResponse.success(HttpStatus.OK, productResponseDto);
    }

    @GetMapping("/api/product")
    public ApiResponse<List<ProductResponseDto>> readAll() {
        List<ProductResponseDto> productResponseDtos = productQueryService.readAll();
        return ApiResponse.success(HttpStatus.OK, productResponseDtos);
    }

    @GetMapping("/api/product/search")
    public ApiResponse<List<ProductResponseDto>> search(@Valid @RequestBody ProductSearchRequestDto productSearchRequestDto){
        List<ProductResponseDto> productResponseDtos = productQueryService.search(productSearchRequestDto);
        return ApiResponse.success(HttpStatus.OK, productResponseDtos);
    }
}
