package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.domain.Result;
import com.geon.onedayonecommit.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github/")
public class ApiGithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("users/{userId}/commit")
    public String searchTodayCommitByUserId(@PathVariable String userId) throws JsonProcessingException {
        Result result = githubService.getJsonDataWhereTodayCommitByUserId(userId);
        return result.message();
    }
}
