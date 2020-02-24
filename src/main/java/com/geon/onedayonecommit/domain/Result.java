package com.geon.onedayonecommit.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Result {
    @JsonProperty("total_count")
    private int todayCommitCount;

    public boolean isCommitToday() {
        return todayCommitCount > 0;
    }

    public String message() {
        if (isCommitToday()) {
            return "잘했어요!! 커밋 횟수 : " + todayCommitCount + "를 달성했네요!! 꾸준히 고고";
        }
        return "커밋 안하고 뭐함?";
    }
}
