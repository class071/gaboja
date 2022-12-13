package com.daily.gaboja.product.service;

import com.daily.gaboja.product.domain.Product;
import com.daily.gaboja.product.exception.InvalidAmountException;
import com.daily.gaboja.product.exception.NoSuchProductExist;
import com.daily.gaboja.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateProductInCart(long id, long amount) {
        isValidAmount(amount, id);
        isProductExist(id);
    }

    private void isProductExist(long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new NoSuchProductExist(id);
        }
    }

    private void isValidAmount(long amount, long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchProductExist::new);
        long stock = product.getStock();
        if (isAmountMoreThanStock(amount, stock)) {
            throw new InvalidAmountException(findNameById(id));
        }
    }

    private String findNameById(Long id) {
        return productRepository.findNameById(id);
    }

    private boolean isAmountMoreThanStock(long amount, long stock) {
        return amount > stock;
    }
}
