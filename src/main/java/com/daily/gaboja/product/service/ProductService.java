package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductCreateRequest;
import com.daily.gaboja.product.dto.ProductResponse;
import com.daily.gaboja.product.dto.ProductUpdateRequest;
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
    public ProductResponse add(ProductCreateRequest productCreateRequest) {
        Product newProduct = productRepository.save(productCreateRequest.toEntity());
        ProductResponse response = new ProductResponse();
        return response.toDto(newProduct);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest productUpdateRequest) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        updateProduct.update(productUpdateRequest.getName(),
                productUpdateRequest.getDescription(),
                productUpdateRequest.getStock(),
                productUpdateRequest.getCategory());
        ProductResponse response = new ProductResponse();
        return response.toDto(updateProduct);
    }

    @Transactional
    public void remove(Long id) {
        Product removeProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        productRepository.delete(removeProduct);
    }

    public ProductResponse readOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExist());
        ProductResponse response = new ProductResponse();
        return response.toDto(product);
    }

    public List<ProductResponse> readAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse().toDto(product))
                .collect(Collectors.toList());
        return productResponses;
    }
}
