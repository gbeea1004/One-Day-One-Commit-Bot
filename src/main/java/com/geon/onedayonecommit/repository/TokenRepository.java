package com.geon.onedayonecommit.repository;

import com.geon.onedayonecommit.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByUserId(Integer userId);
}
