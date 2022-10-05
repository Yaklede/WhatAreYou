package com.WhatAreYou.WhatAreYou.dto.form.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentForm {
    private Long id;
    @NotEmpty
    private String commentInput;
}
