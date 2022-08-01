package com.daily.gaboja.cart.service;

import com.daily.gaboja.cart.dto.CartReadResponseDto;
import com.daily.gaboja.cart.dto.CartRegisterDto;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.exception.WrongUserIdException;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.exception.UserNotExistException;
import com.daily.gaboja.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Long create(Long userId) throws WrongUserIdException {
        CartRegisterDto cartRegisterDto = new CartRegisterDto(userId);
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        cartRepository.save(cartRegisterDto.toEntity(user));
        return userId;
    }

    public Long delete(Long userId) throws WrongUserIdException {
        cartRepository.deleteById(userId);
        return userId;
    }

    @Transactional(readOnly = true)
    public CartReadResponseDto get(Long userId) {
        CartReadResponseDto dto = new CartReadResponseDto();
        return dto.convertEntityToDto(cartRepository.findById(userId).orElseThrow(CartNotExistException::new));
    }
}
