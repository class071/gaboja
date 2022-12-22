package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.repository.ProductRepository;
import java.util.List;

public enum ProductSearcher {

    NAME {
        @Override
        public List<Product> findProductByKeyword(ProductRepository productRepository, String keyword) {
            return productRepository.findByName(keyword);
        }
    },
    CATEGORY {
        @Override
        public List<Product> findProductByKeyword(ProductRepository productRepository, String keyword) {
            return productRepository.findByCategory(keyword);
        }
    };

    public abstract List<Product> findProductByKeyword(ProductRepository productRepository, String keyword);
}
