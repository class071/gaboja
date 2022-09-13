package com.daily.gaboja.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-access-token-client", url = "${naver.access.token.url}")
public interface NaverAccessTokenClient {

    @PostMapping
    String getAccessToken(@RequestParam("grant_type") String grantType,
                                 @RequestParam("client_id") String clientId,
                                 @RequestParam("client_secret") String clientSecret,
                                 @RequestParam("code") String code,
                                 @RequestParam("state") String state);
}
