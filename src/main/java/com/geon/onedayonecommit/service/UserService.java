package com.geon.onedayonecommit.service;

import com.geon.onedayonecommit.config.auth.dto.SessionUser;
import com.geon.onedayonecommit.domain.user.User;
import com.geon.onedayonecommit.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;

    public User findUserByUserId(Integer userId) {
        return userRepository.findById(userId)
                             .orElseThrow(IllegalArgumentException::new);
    }

    public List<User> findAllUsersWhereGithubIdIsNotNull() {
        List<User> users = userRepository.findAllByGithubIdIsNotNull();
        if (users.size() == 0) {
            throw new IllegalArgumentException("유저가 없습니다.");
        }
        return users;
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
