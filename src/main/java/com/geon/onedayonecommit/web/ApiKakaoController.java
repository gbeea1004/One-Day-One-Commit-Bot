package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.domain.user.User;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.KakaoService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kakao/")
@RequiredArgsConstructor
public class ApiKakaoController {
    private static final Logger log = LoggerFactory.getLogger(ApiKakaoController.class);
    private final KakaoService kakaoService;
    private final GithubService githubService;
    private final UserService userService;

    @Scheduled(cron = "0 0 23 * * *") // 초 - 분 - 시 - 일 - 월 - 요일
    public void run() throws JsonProcessingException {
        log.debug("시간 됬음");
        List<User> users = userService.findAllUsersWhereGithubIdIsNotNull();

        for (User user : users) {
            sendMessageToMe(user.getId());
        }
    }

    @PostMapping("sendMessage/users/{userId}")
    public void sendMessageToMe(@PathVariable Integer userId) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
        if (result.isTodayCommit()) {
            log.debug("깃허브 아이디 : {}, 오늘 커밋 수 : {}", githubId, result.getTodayCommitCount());
            return;
        }
        kakaoService.sendMessageToMe(userId);
    }
}
