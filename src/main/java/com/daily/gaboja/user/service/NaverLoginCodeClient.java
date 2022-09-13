package com.daily.gaboja.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-login-code-client", url = "${naver.login.code.url}")
public interface NaverLoginCodeClient {

    @PostMapping
    String login(@RequestParam("grant_type") String grantType,
                 @RequestParam("client_id") String clientId,
                 @RequestParam("redirect_uri") String redirectUri,
                 @RequestParam("state") String state);
}
