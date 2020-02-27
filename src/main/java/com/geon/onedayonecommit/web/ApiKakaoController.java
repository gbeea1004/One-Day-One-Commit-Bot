package com.geon.onedayonecommit.web;

import com.geon.onedayonecommit.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kakao/")
@RequiredArgsConstructor
public class ApiKakaoController {
    private final KakaoService kakaoService;

    @PostMapping("sendMessage/users/{userId}")
    public void sendMessageToMe(@PathVariable Integer userId) {
        kakaoService.sendMessageToMe(userId);
    }
}
