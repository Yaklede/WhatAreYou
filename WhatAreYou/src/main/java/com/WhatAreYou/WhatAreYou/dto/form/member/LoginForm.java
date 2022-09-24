package com.WhatAreYou.WhatAreYou.dto.form.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginForm {
    private String loginId;
    private String password;
    private String nickName;
    private String email;
    private int age;

}
