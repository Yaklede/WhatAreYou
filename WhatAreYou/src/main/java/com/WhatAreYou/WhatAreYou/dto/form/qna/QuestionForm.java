package com.WhatAreYou.WhatAreYou.dto.form.qna;

import lombok.Data;

@Data
public class QuestionForm {
    private Long qnaId;
    private String question;
    private String answer;


    public QuestionForm() {
    }

    public QuestionForm(Long qnaId, String question, String answer) {
        this.qnaId = qnaId;
        this.question = question;
        this.answer = answer;
    }

    public QuestionForm(Long qnaId) {
        this.qnaId = qnaId;
    }
}
