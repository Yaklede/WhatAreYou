package com.WhatAreYou.WhatAreYou.dto.form;

import lombok.Data;

@Data
public class MemberSearchCondition {
    private String username;
    private Integer ageGoe;
    private Integer ageLoe;
}
