package com.WhatAreYou.WhatAreYou.service.qna;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QnA;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.QnaNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.repository.qna.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long question(Long memberId, String question) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        QnA qnA = QnA.builder()
                .member(member)
                .questions(question)
                .build();
        qnaRepository.save(qnA);
        return qnA.getId();
    }

    @Transactional
    @Override
    public Long answer(Long qnaId, String answer) {
        QnA findQna = qnaRepository.findById(qnaId).orElseThrow(() -> new QnaNotFoundException());
        findQna.setAnswers(answer);
        return findQna.getId();
    }

    @Override
    public List<QnA> findAll() {
        return qnaRepository.findAll();
    }

    @Override
    public Long notAnswerCount(Long qnaId) {
        return qnaRepository.findByNotAnswerCount(qnaId);
    }

    @Override
    public List<QnA> notAnswer(Long qnaId) {
        return qnaRepository.findByNotAnswer(qnaId);
    }
}
