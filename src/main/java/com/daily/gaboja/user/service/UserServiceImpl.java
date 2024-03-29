package com.daily.gaboja.user.service;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.dto.CartRegisterDto;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
import com.daily.gaboja.user.dto.NaverLoginDto;
import com.daily.gaboja.user.dto.LoginResponse;
import com.daily.gaboja.user.dto.NaverProfile;
import com.daily.gaboja.user.exception.ParseFailedException;
import com.daily.gaboja.user.exception.UserNotExistException;
import com.daily.gaboja.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final TokenService tokenService;

    private final NaverLoginCodeClient naverLoginCodeClient;

    private final NaverAccessTokenClient naverAccessTokenClient;

    private final NaverLoginClient naverLoginClient;

    @Value("${naver.clientId}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.secret}")
    private String NAVER_SECRET_KEY;

    @Value("${naver.callback.url}")
    private String NAVER_CALLBACK_URL;

    @Override
    public String getLoginCode() {
        try {
            String redirectURI = URLEncoder.encode(NAVER_CALLBACK_URL, "UTF-8");

            return naverLoginCodeClient.login("authorization_code", NAVER_CLIENT_ID, redirectURI, "10");

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public LoginResponse getAccessToken(String code) {
        try {
            String result = naverAccessTokenClient.getAccessToken("authorization_code", NAVER_CLIENT_ID, NAVER_SECRET_KEY, code, "10");

            JSONObject naverObject = parseJSON(result);
            String accessToken = (String) naverObject.get("access_token");

            return loginWithAccessToken(accessToken);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseFailedException();
        }
    }

    private JSONObject parseJSON(String result) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            return jsonObject;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParseFailedException();
        }
    }

    @Override
    public LoginResponse loginWithAccessToken(String token) {
        NaverLoginDto naverLoginDto = naverLoginClient.loginWithAccessToken("Bearer " + token, "application/x-www-form-urlencoded;charset=utf-8");

        UserRole userRole = UserRole.CUSTOMER;

        NaverProfile naverProfile = new NaverProfile(naverLoginDto.getName(), naverLoginDto.getEmail(),
                naverLoginDto.getProfile(), naverLoginDto.getGender(), naverLoginDto.getAge(), userRole);
        CartRegisterDto cartRegisterDto;
        if (!userRepository.findByEmail(naverLoginDto.getEmail()).isPresent()) {
            User user = userRepository.save(naverProfile.toEntity());
            cartRegisterDto = new CartRegisterDto(user.getId());
            Cart cart = cartRegisterDto.toEntity(user);
            cartRepository.save(cart);
            user.setCart(cart);
        }

        return createJwtToken(naverLoginDto.getEmail());
    }

    public LoginResponse createJwtToken(String email) {
        String accessToken = tokenService.createAccessToken(email);
        String refreshToken = tokenService.createRefreshToken(email);

        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    public String requestSellerRole(long id){
        return changeUserRole(id);
    }

    private String changeUserRole(long id){
        User user = userRepository.findById(id).orElseThrow(UserNotExistException::new);
        user.grantSellerRole();
        return user.getRole().toString();
    }

    @Override
    public void delete(long id){
        userRepository.deleteById(id);
    }
}
