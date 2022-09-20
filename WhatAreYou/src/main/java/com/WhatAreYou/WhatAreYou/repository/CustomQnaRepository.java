package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.QnA;

import java.util.List;

public interface CustomQnaRepository {
    public List<QnA> findByNotAnswer(Long qnaId);
    public int findByNotAnswerCount(Long qnaId);
}
