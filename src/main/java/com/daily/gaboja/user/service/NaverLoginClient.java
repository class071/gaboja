package com.daily.gaboja.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-login-client", url = "${naver.get.userinfo.url}")
public interface NaverLoginClient {

    @PostMapping
    String loginWithAccessToken(@RequestHeader("Authorization") String token,
                                      @RequestHeader("Content-type") String contentType);
}
