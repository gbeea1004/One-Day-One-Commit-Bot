package com.geon.onedayonecommit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Result {
    @JsonProperty("total_count")
    private int todayCommitCount;

    public boolean isTodayCommit() {
        return todayCommitCount != 0;
    }

    public Result(int todayCommitCount) {
        this.todayCommitCount = todayCommitCount;
    }
}
