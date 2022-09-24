package com.WhatAreYou.WhatAreYou.dto.form.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JoinForm {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String email;
    @NotNull
    private int age;
}
