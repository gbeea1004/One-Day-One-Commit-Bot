package com.geon.onedayonecommit.web;

import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // TODO : hidden PUT으로 줬는데 왜 POST로 요청을 보내냐..
    @PostMapping("{userId}")
    public String updateGithubId(@PathVariable Integer userId, String githubId) {
        userService.updateGithubId(userId, githubId);
        return "redirect:/";
    }
}
