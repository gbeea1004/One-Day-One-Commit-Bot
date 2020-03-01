package com.geon.onedayonecommit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geon.onedayonecommit.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import util.MediaType;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final RestTemplate restTemplate;

    public boolean isExistUser(String githubId) {
        try {
            restTemplate.getForEntity("https://api.github.com/users/" + githubId, String.class);
        } catch (HttpClientErrorException e) {
            return false;
        }
        return true;
    }

    public Result getJsonDataWhereTodayCommitByGithubId(String githubId) throws JsonProcessingException {
        final String URL = "https://api.github.com/search/commits?q=author:%s+committer-date:>=%s&sort=committer-date&order=desc";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.GIT_HUB_COMMIT.getType());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        // 무슨 이유인지는 모르겠지만 committer-date 필터에 문제가 있는 것 같다..
        // committer-date:2020-02-28로 날짜를 불러오면 2020-02-29 커밋도 가져오는 현상이 발생한다.
        // 따라서 모든 날짜의 데이터를 가져오는 것은 성능상 이슈가 있으므로
        // 안전 상 10일 전부터 현재 시간까지의 데이터를 모두 불러와, 내림차순 정렬하여 날짜를 확인하는 방식으로 진행한다.
        String jsonData = restTemplate.exchange(String.format(URL, githubId, LocalDate.now().minusDays(10).toString()), HttpMethod.GET, httpEntity, String.class).getBody();
        return _toResult(jsonData);
    }

    private Result _toResult(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        for (JsonNode node : jsonNode) {
            System.out.println("노드 : " + node.asText());
        }

        if (jsonNode.get("total_count").asText().equals("0")) {
            return new Result(false);
        }
        JsonNode node = jsonNode.get("items").get(0).get("commit").get("committer").get("date");
        String recentCommitTime = node.asText().split("T")[0];
        System.out.println("최근 커밋 시간 : " + recentCommitTime);
        System.out.println("현재 날짜 : " + LocalDate.now().toString());
        return new Result(recentCommitTime.equals(LocalDate.now().toString()));
    }
}
