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
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용을 입력하세요")
    private String content;
    //hashTag는 ,로 구분하고 #을 붙여서 저장을 해준다; 태그는 넣지 않아도 된다.
    private String hashTag;
    @NotNull(message = "이미지")
    private MultipartFile file;


    public BoardForm() {

    }
}
