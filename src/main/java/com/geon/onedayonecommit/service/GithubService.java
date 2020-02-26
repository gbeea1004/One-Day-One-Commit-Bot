package com.geon.onedayonecommit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geon.onedayonecommit.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import util.MediaType;

import java.time.LocalDate;

@Service
public class GithubService {
    private final String URL = "https://api.github.com/search/commits?q=author:%s+committer-date:>=%s";

    @Autowired
    private RestTemplate restTemplate;

    public Result getJsonDataWhereTodayCommitByUserId(String userId) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.GIT_HUB_COMMIT.getType());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String jsonData = restTemplate.exchange(String.format(URL, userId, LocalDate.now().toString()), HttpMethod.GET, httpEntity, String.class).getBody();
        return _toResult(jsonData);
    }

    private Result _toResult(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 존재하지 않는 필드 무시
        return objectMapper.readValue(jsonData, Result.class);
    }
}
