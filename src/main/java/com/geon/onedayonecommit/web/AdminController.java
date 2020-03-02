package com.geon.onedayonecommit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geon.onedayonecommit.domain.user.User;
import com.geon.onedayonecommit.dto.Result;
import com.geon.onedayonecommit.service.GithubService;
import com.geon.onedayonecommit.service.KakaoService;
import com.geon.onedayonecommit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final GithubService githubService;
    private final KakaoService kakaoService;

    @GetMapping("form")
    public String adminForm() {
        return "admin/form";
    }

    @GetMapping("users")
    public String findAllMembers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/user_list";
    }

    @PostMapping("users/sendMessage")
    public String sendMessageToMembersWhoHaveNotYetCommitted(Model model) throws JsonProcessingException {
        List<User> users = userService.findAllUsers();
        for (User user : users) {
            String githubId = user.getGithubId();
            if (githubService.isExistUser(githubId)) {
                Result result = githubService.getJsonDataWhereTodayCommitByGithubId(githubId);
                if (!result.isCommitted()) {
                    kakaoService.sendMessageToMe(user.getId());
                    log.debug("{}님에게 메시지를 전송하였습니다!", user.getName());
                }
            } else {
                log.debug("{}님의 깃허브 아이디가 존재하지 않습니다.", user.getName());
            }
        }
        model.addAttribute("message", "메시지를 발송했습니다.");
        return "redirect:/admin/form";
    }
}
