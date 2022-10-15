package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductCreateRequestDto;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.dto.ProductUpdateRequestDto;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import com.daily.gaboja.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(NoSuchProductExist::new);
        updateProduct.update(productUpdateRequestDto);
        return new ProductResponseDto().toDto(updateProduct);
    }

    @Transactional
    public Long remove(Long id) {
        Product removeProduct = productRepository.findById(id)
                .orElseThrow(NoSuchProductExist::new);
        productRepository.delete(removeProduct);
        return id;
    }

    public ProductResponseDto readOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchProductExist::new);
        return new ProductResponseDto().toDto(product);
    }

    public List<ProductResponseDto> readAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponsDtos = products.stream()
                .map(product -> new ProductResponseDto().toDto(product))
                .toList();
        return productResponsDtos;
    }
}