package com.geon.onedayonecommit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.domain.token.Token;
import com.geon.onedayonecommit.domain.token.TokenRepository;
import com.geon.onedayonecommit.dto.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private static final Logger log = LoggerFactory.getLogger(KakaoService.class);
    private static final String HOST = "https://kapi.kakao.com";
    private static final Integer TEMPLATE_ID = 20859;
    private final TokenRepository tokenRepository;
    private final RestTemplate restTemplate;
    private final GithubService githubService;

    public void sendMessageToMe(Integer userId) throws JsonProcessingException {
        Result result = githubService.getJsonDataWhereTodayCommitByUserId(userId);
        if (result.isTodayCommit()) {
            log.debug("오늘 커밋 수 : {}", result.getTodayCommitCount());
            return;
        }

        final String URL = HOST + "/v2/api/talk/memo/send";
        HttpHeaders headers = new HttpHeaders();
        Token token = tokenRepository.findByUserId(userId)
                                     .orElseThrow(IllegalArgumentException::new);

        headers.set("Authorization", "Bearer " + token.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_id", TEMPLATE_ID);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parameters, headers);
        restTemplate.postForEntity(URL, httpEntity, String.class);
    }
}
