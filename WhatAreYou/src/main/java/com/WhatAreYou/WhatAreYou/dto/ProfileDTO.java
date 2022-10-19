package com.WhatAreYou.WhatAreYou.dto;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfileDTO {

    private Long loginMemberId;
    private String nickName;
    private LocalDateTime create_at;
    private Long memberFileId;
    private Long followingCount;
    private Long followerCount;
    private Long boardCount;
    private List<BoardDTO> boardDTOS;

    @Builder
    public ProfileDTO(List<BoardDTO> boards, Member loginMember, Long followingCount, Long followerCount,Long boardCount) {
        this.nickName = loginMember.getLoginId();
        this.create_at = loginMember.getCreate_at();
        this.memberFileId = loginMember.getFileEntity().getId();
        this.loginMemberId = loginMember.getId();
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.boardCount = boardCount;
        this.boardDTOS = boards;
    }
}
