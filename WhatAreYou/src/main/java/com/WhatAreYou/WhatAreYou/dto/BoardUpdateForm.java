package com.WhatAreYou.WhatAreYou.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BoardUpdateForm {
    private String title;
    private String content;

    @Builder
    public BoardUpdateForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
