package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.domain.user.User;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.KakaoService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
    private final KakaoService kakaoService;
    private final GithubService githubService;
    private final UserService userService;

    @Scheduled(cron = "0 0 23 * * *") // 초 - 분 - 시 - 일 - 월 - 요일
    public void run() throws JsonProcessingException {
        log.debug("시간 됬음");
        List<User> users = userService.findAllUsersWhereGithubIdIsNotNull();

        for (User user : users) {
            sendMessage(user.getId());
        }
    }

    private void sendMessage(Integer userId) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
        if (result.isCommitted()) {
            log.debug("깃허브 아이디 : {}, 커밋완료", githubId);
            return;
        }
        log.debug("{}님이 아직 커밋을 하지 않아 메시지를 보냅니다.", githubId);
        kakaoService.sendMessageToMe(userId);
    }
}
