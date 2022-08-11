package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductCreateRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.dto.ProductUpdateRequestDto;
import com.daily.gaboja.product.exception.ProductNotExist;
import com.daily.gaboja.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto add(ProductCreateRequestDto productCreateRequestDto) {
        Product newProduct = productRepository.save(productCreateRequestDto.toEntity());
        return new ProductResponseDto().toDto(newProduct);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        updateProduct.update(productUpdateRequestDto);
        return new ProductResponseDto().toDto(updateProduct);
    }

    @Transactional
    public void remove(Long id) {
        Product removeProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        productRepository.delete(removeProduct);
    }

    public ProductResponseDto readOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        return new ProductResponseDto().toDto(product);
    }

    public List<ProductResponseDto> readAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponsDtos = products.stream()
                .map(product -> new ProductResponseDto().toDto(product))
                .collect(Collectors.toList());
        return productResponsDtos;
    }
}
