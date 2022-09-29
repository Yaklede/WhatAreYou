package com.WhatAreYou.WhatAreYou.dto.form.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BoardForm {
    private Long id;
    private String createdId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private MultipartFile file;

    public BoardForm() {

    }
}
