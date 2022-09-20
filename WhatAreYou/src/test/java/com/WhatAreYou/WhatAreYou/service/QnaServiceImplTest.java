package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QnA;
import com.WhatAreYou.WhatAreYou.repository.MemberRepository;
import com.WhatAreYou.WhatAreYou.repository.QnaRepository;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QnaServiceImplTest {
    @Autowired
    QnaService qnaService;
    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        memberRepository.deleteAll();
        qnaRepository.deleteAll();
    }

    @Test
    public void createQna() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .nickName("nick")
                .age(1)
                .email("e")
                .build();
        memberRepository.save(member);
        //when
        Long questionId = qnaService.question(member.getId(), "질문입니다.");
        //then
        QnA qnA = qnaRepository.findById(questionId).get();
        Assertions.assertThat(qnA.getQuestions()).isEqualTo("질문입니다.");
    }

    @Test
    public void notAnswerCount() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .nickName("nick")
                .age(1)
                .email("e")
                .build();
        memberRepository.save(member);
        //when
        Long questionId = qnaService.question(member.getId(), "질문입니다.");
        //when
        int count = qnaService.notAnswerCount(questionId);
        Member noAnswer = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(count).isEqualTo(1);
        qnaService.answer(questionId, "대답입니다.");
        //then
        int answerCount = qnaService.notAnswerCount(questionId);
        Member answer = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(answerCount).isEqualTo(0);

        Assertions.assertThat(noAnswer).isEqualTo(answer);

    }

    @Test
    public void notAnswer() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .nickName("nick")
                .age(1)
                .email("e")
                .build();
        memberRepository.save(member);
        //when
        Long questionId = qnaService.question(member.getId(), "질문입니다.");
        //when
        List<QnA> notAnswer = qnaService.notAnswer(questionId);
        Assertions.assertThat(notAnswer.get(0).getQuestions()).isEqualTo("질문입니다.");
        //then
        qnaService.answer(questionId, "대답입니다.");
        QnA qnA = qnaRepository.findById(questionId).get();
        Assertions.assertThat(qnA.getAnswers()).isEqualTo("대답입니다.");
    }
    @Test
    public void findAll() throws Exception {
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .nickName("nick")
                .age(1)
                .email("e")
                .build();
        memberRepository.save(member);
        qnaService.question(member.getId(), "질문입니다.");
        List<QnA> findQna = qnaService.findAll();
        Assertions.assertThat(findQna.size()).isEqualTo(1);
    }

}