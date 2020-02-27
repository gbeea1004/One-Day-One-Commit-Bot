package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/github/")
public class ApiGithubController {
    private final GithubService githubService;
    private final UserService userService;

    @GetMapping("users/{userId}/commit")
    public Result searchTodayCommitByUserId(@PathVariable Integer userId) throws JsonProcessingException {
        String githubId = userService.findUserByUserId(userId).getGithubId();
        return githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
    }
}
