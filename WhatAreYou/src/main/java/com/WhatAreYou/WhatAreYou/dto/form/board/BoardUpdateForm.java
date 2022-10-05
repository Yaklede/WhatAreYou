package com.WhatAreYou.WhatAreYou.dto.form.board;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BoardUpdateForm {
    private String title;
    private String content;
    private String hashTag;
    private MultipartFile file;
    private Long fileId;

    @Builder
    public BoardUpdateForm(String title, String content, String hashTag, MultipartFile file) {
        this.title = title;
        this.content = content;
        this.hashTag = hashTag;
        this.file = file;
    }
}
