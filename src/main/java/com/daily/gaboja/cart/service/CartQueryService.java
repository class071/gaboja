package com.daily.gaboja.cart.service;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryService {

    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public CartReadResponseDto get(Long userId) {
        return CartReadResponseDto.from(cartRepository.findById(userId).orElseThrow(CartNotExistException::new));
    }
}
