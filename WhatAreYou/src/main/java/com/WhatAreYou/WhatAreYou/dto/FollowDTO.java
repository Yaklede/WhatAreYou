package com.WhatAreYou.WhatAreYou.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FollowDTO {
    private Long fileId;
    private String followNickName;
    private LocalDateTime created;
    private Long boardCount;
    private Long followerCount;

    public FollowDTO(Long fileId, String followNickName, LocalDateTime created, Long followerCount,Long boardCount) {
        this.fileId = fileId;
        this.followNickName = followNickName;
        this.created = created;
        this.boardCount = boardCount;
        this.followerCount = followerCount;
    }
}
