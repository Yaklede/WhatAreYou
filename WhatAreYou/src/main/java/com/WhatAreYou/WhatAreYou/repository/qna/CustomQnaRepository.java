package com.WhatAreYou.WhatAreYou.repository.qna;

import com.WhatAreYou.WhatAreYou.domain.QnA;

import java.util.List;

public interface CustomQnaRepository {
    public List<QnA> findByNotAnswer(Long qnaId);
    public Long findByNotAnswerCount(Long qnaId);
}
