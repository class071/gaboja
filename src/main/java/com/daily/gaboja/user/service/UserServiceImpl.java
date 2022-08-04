package com.daily.gaboja.user.service;

import com.daily.gaboja.cart.domain.Cart;
import com.daily.gaboja.cart.dto.CartRegisterDto;
import com.daily.gaboja.cart.exception.CartNotExistException;
import com.daily.gaboja.cart.repository.CartRepository;
import com.daily.gaboja.jwt.TokenService;
import com.daily.gaboja.user.constant.UserRole;
import com.daily.gaboja.user.domain.User;
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
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final TokenService tokenService;

    @Value("${naver.clientId}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.secret}")
    private String NAVER_SECRET_KEY;

    @Value("${data.apikey}")
    private String DATA_APIKEY;

    @Value("${naver.callback.url}")
    private String NAVER_CALLBACK_URL;

    @Value("${naver.login.code.url}")
    private String NAVER_LOGIN_CODE_URL;

    @Value("${naver.access.token.url}")
    private String NAVER_ACCESS_TOKEN_URL;

    @Value("${naver.get.userinfo.url}")
    private String NAVER_GET_USERINFO_URL;
    @Override
    public String getLoginCode() {
        try {
            String redirectURI = URLEncoder.encode(NAVER_CALLBACK_URL, "UTF-8");
            String apiURL = NAVER_LOGIN_CODE_URL;

            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + NAVER_CLIENT_ID);
            sb.append("&redirect_uri=" + redirectURI);
            sb.append("&state=" + "10");
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public LoginResponse getAccessToken(String code) {
        try {
            String apiURL = NAVER_ACCESS_TOKEN_URL;

            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + NAVER_CLIENT_ID);
            sb.append("&client_secret=" + NAVER_SECRET_KEY);
            sb.append("&code=" + code);
            sb.append("&state=" + "10");
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);
            JSONObject naverObject = parseJSON(result);
            String accessToken = (String) naverObject.get("access_token");
            System.out.println("accessToken = " + accessToken);
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
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);
        rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        rt.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });

        ResponseEntity<String> response = rt.exchange(
                NAVER_GET_USERINFO_URL,
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );

        String str = response.getBody();
        JSONObject naver_response = parseJSON(str);
        JSONObject naver_account = (JSONObject) naver_response.get("response");
        String email = (String) naver_account.get("email");
        String name = (String) naver_account.get("name");
        String profile = (String) naver_account.get("profile_image");
        String gender = (String) naver_account.get("gender");
        String age = (String) naver_account.get("age");

        UserRole userRole = UserRole.CUSTOMER;

        NaverProfile naverProfile = new NaverProfile(name, email, profile, gender, age, userRole);
        CartRegisterDto cartRegisterDto;
        if (!userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.save(naverProfile.toEntity());
            cartRegisterDto = new CartRegisterDto(user.getId());
            Cart cart = cartRegisterDto.toEntity(user);
            cartRepository.save(cart);
            user.setCart(cart);
        }

        return createJwtToken(email);
    }

    public LoginResponse createJwtToken(String email) {
        String accessToken = tokenService.createAccessToken(email);
        String refreshToken = tokenService.createRefreshToken(email);

        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    public String requestSellerRole(String b_no, long id){
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
