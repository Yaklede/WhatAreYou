package com.WhatAreYou.WhatAreYou.dto.form.member;

import lombok.Data;

@Data
public class MemberSearchCondition {
    private String username;
    private Integer ageGoe;
    private Integer ageLoe;
}
