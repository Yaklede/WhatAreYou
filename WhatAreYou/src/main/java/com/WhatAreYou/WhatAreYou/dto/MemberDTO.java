package com.WhatAreYou.WhatAreYou.dto;

import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberDTO {
    private Long memberId;
    private Long fileId;
    private String loginId;

    @Builder
    public MemberDTO(Member member) {
        this.memberId = member.getId();
        this.fileId = member.getFileEntity().getId();
        this.loginId = member.getLoginId();
    }
}

