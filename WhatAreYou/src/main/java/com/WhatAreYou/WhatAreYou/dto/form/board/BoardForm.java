package com.WhatAreYou.WhatAreYou.dto.form.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardForm {
    private Long id;
    private String loginId;
    private String title;
    private String content;
    private MultipartFile file;
}
