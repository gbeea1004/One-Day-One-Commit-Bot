package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.KakaoService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kakao/")
@RequiredArgsConstructor
public class ApiKakaoController {
    private static final Logger log = LoggerFactory.getLogger(ApiKakaoController.class);
    private final KakaoService kakaoService;
    private final GithubService githubService;
    private final UserService userService;

    @PostMapping("sendMessage/users/{userId}")
    public void sendMessageToMe(@PathVariable Integer userId) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
        if (result.isTodayCommit()) {
            log.debug("오늘 커밋 수 : {}", result.getTodayCommitCount());
            return;
        }
        kakaoService.sendMessageToMe(userId);
    }
}
