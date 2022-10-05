package com.WhatAreYou.WhatAreYou.dto.form.board;

import lombok.Data;

@Data
public class BoardSearchCondition {
    private String title;
    private String tag;
    private String query;
}
