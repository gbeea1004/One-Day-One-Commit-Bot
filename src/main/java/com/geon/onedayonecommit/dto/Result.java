package com.geon.onedayonecommit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Result {
    @JsonProperty("total_count")
    private int todayCommitCount;
}
