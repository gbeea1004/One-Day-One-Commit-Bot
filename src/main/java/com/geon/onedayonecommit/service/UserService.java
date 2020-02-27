package com.geon.onedayonecommit.service;

import com.geon.onedayonecommit.config.auth.dto.SessionUser;
import com.geon.onedayonecommit.domain.user.User;
import com.geon.onedayonecommit.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;

    @Transactional(readOnly = true)
    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId)
                             .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void updateGithubId(Integer userId, String githubId) {
        User user = findUserByUserId(userId);
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (user.getId()
                .equals(sessionUser.getId())) {
            user.updateGithubId(githubId);
            sessionUser.updateGithubId(githubId);
        }
    }
}
