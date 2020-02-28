package com.geon.onedayonecommit.web;

import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("{id}/form")
    public String updateGithubIdForm(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return "user/update_github_id_form";
    }

    // TODO : hidden PUT으로 줬는데 왜 POST로 요청을 보내냐..
    @PostMapping("{userId}")
    public String updateGithubId(@PathVariable Integer userId, String githubId, Model model) {
        if (githubService.isExistUser(githubId)) {
            userService.updateGithubId(userId, githubId);
            return "redirect:/";
        }
        log.debug("존재 하지 않는 깃허브 아이디 입니다.");
        model.addAttribute("id", userId);
        return "user/update_github_id_failed_form";
    }
}
