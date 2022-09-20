package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QnA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QnaService {
    public Long question(Long memberId, String question);
    public Long answer(Long qnaId, String answer);
    public List<QnA> findAll();
    public int notAnswerCount(Long qnaId);
    public List<QnA> notAnswer(Long qnaId);
}
