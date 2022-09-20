package com.WhatAreYou.WhatAreYou.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberUpdateForm {
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
}
