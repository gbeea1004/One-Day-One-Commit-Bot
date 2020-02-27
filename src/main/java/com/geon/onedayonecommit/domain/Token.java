package com.geon.onedayonecommit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Token {
    @Id
    private Integer userId;

    @Column(nullable = false)
    private String accessToken;

    public Token(Integer userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }
}
