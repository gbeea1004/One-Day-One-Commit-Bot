package com.geon.onedayonecommit.web.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geon.onedayonecommit.domain.Result;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import util.MediaType;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/github/")
public class ApiGithubController {
    private final String URL = "https://api.github.com/search/commits?q=author:%s+committer-date:>=%s";

    @GetMapping("users/{userId}/commit")
    public String searchTodayCommitByUserId(@PathVariable String userId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.GIT_HUB_COMMIT.getType());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String body = restTemplate.exchange(String.format(URL, userId, LocalDate.now().toString()), HttpMethod.GET, httpEntity, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 존재하지 않는 필드 무시
        Result result = objectMapper.readValue(body, Result.class);
        return result.message();
    }
}
