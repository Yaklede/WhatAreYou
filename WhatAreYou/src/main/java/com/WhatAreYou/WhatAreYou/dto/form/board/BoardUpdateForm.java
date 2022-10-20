package com.WhatAreYou.WhatAreYou.dto.form.board;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.HashTag;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BoardUpdateForm {
    private String createdId;
    private String title;
    private String content;
    private String hashTag;
    private MultipartFile file;
    private FileEntity fileEntity;
    private Long boardId;
    private HashTag hashTagEntity;

    @Builder
    public BoardUpdateForm(String title, String content, String hashTag, MultipartFile file , Long boardId , String createdId) {
        this.title = title;
        this.createdId = createdId;
        this.content = content;
        this.hashTag = hashTag;
        this.boardId = boardId;
        this.file = file;
    }
}
