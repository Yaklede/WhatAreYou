package com.WhatAreYou.WhatAreYou.dto;

import com.WhatAreYou.WhatAreYou.domain.Comment;
import lombok.Data;

@Data
public class CommentDTO {
    private Long commentId;
    private String nickName;
    private String comment;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.nickName = comment.getMember().getNickName();
    }
}
