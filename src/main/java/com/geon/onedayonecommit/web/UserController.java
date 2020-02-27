package com.geon.onedayonecommit.web;

import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final GithubService githubService;

    // TODO : hidden PUT으로 줬는데 왜 POST로 요청을 보내냐..
    @PostMapping("{userId}")
    public String updateGithubId(@PathVariable Integer userId, String githubId) {
        if (!githubService.isExistUser(githubId)) {
            log.debug("존재 하지 않는 깃허브 아이디 입니다.");
        }
        userService.updateGithubId(userId, githubId);
        return "redirect:/";
    }
}
