package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.KakaoService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/{userId}/")
@RequiredArgsConstructor
@Slf4j
public class CommitBotController {
    private final KakaoService kakaoService;
    private final GithubService githubService;
    private final UserService userService;

    @GetMapping("commits")
    public String confirmCommitCountToday(@PathVariable Integer userId, Model model) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
        log.debug("오늘 커밋 수 : {}", result.getTodayCommitCount());
        model.addAttribute("confirmCommit", result);
        return "index";
    }

    @PostMapping("sendMessage")
    public String sendMessage(@PathVariable Integer userId) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
        if (result.isTodayCommit()) {
            log.debug("깃허브 아이디 : {}, 오늘 커밋 수 : {}", githubId, result.getTodayCommitCount());
            return "redirect:/";
        }
        kakaoService.sendMessageToMe(userId);
        return "redirect:/";
    }
}
