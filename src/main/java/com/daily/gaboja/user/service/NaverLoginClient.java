package com.daily.gaboja.user.service;

import com.daily.gaboja.user.dto.NaverLoginDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-login-client", url = "${naver.get.userinfo.url}")
public interface NaverLoginClient {

    @PostMapping
    NaverLoginDto loginWithAccessToken(@RequestHeader("Authorization") String token,
                                       @RequestHeader("Content-type") String contentType);
}
