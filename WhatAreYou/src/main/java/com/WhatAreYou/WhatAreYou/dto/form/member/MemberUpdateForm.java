package com.WhatAreYou.WhatAreYou.dto.form.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MemberUpdateForm {
    private Long id;
    private String nickName;
    private String password;
    private String email;
    private int age;

    @Builder
    public MemberUpdateForm(String nickName, String password, String email, int age) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public MemberUpdateForm(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.password = member.getPassword();
        this.age = member.getAge();
        this.email = member.getEmail();
    }
}
