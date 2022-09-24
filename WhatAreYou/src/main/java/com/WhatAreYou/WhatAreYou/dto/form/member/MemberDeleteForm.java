package com.WhatAreYou.WhatAreYou.dto.form.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Data;

@Data
public class MemberDeleteForm {
    private Long id;
    private String loginId;
    private String nickName;

    public MemberDeleteForm(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.nickName = member.getNickName();
    }
}
