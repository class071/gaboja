package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.dto.ProductResponseDto;
import com.daily.gaboja.product.dto.ProductSearchRequestDto;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import com.daily.gaboja.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductResponseDto readOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchProductExist::new);
        return new ProductResponseDto().toDto(product);
    }

    public List<ProductResponseDto> readAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto().toDto(product))
                .toList();
    }

    public List<ProductResponseDto> search(ProductSearchRequestDto productSearchRequestDto){
        ProductSearcher productSearcher = ProductSearcher.valueOf(productSearchRequestDto.getType());
        List<Product> products = productSearcher.findProductByKeyword(productRepository, productSearchRequestDto.getKeyword());
        return products.stream()
                .map(product -> new ProductResponseDto().toDto(product))
                .toList();
    }
}
