package com.geon.onedayonecommit.web;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;
import support.test.AcceptanceTest;

import java.net.URI;

public class ApiKakaoControllerTest extends AcceptanceTest {
    private final String CLIENT_ID = "37055d53a7ddf47e7c5c0e56ffee125f";
    private final String REDIRECT_URI = "http://localhost:8080";
    private final String RESPONSE_TYPE = "code";
    private final String URL = "https://kauth.kakao.com";

    @Test
    public void givenOauthValuesWhenLogin_ThenGetAuthorizeCode() {
        URI uri = UriComponentsBuilder.fromHttpUrl(URL + "/oauth/authorize")
                                      .queryParam("client_id", CLIENT_ID)
                                      .queryParam("redirect_uri", REDIRECT_URI)
                                      .queryParam("response_type", RESPONSE_TYPE)
                                      .build()
                                      .toUri();

        String response = template.getForObject(uri, String.class);
        System.out.println(response);
    }
}