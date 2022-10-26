package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QnA;
import com.WhatAreYou.WhatAreYou.dto.form.qna.QuestionForm;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import com.WhatAreYou.WhatAreYou.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class TestQnaController {

    private final MemberService memberService;
    private final QnaService qnaService;

    @GetMapping("/qna/question/{memberId}")
    public String question(@PathVariable("memberId")Long memberId, @ModelAttribute("QuestionForm")QuestionForm form, Model model) {
        Member member = memberService.findByOne(memberId);
        model.addAttribute("member", member);
        return "/test/question";
    }

    @PostMapping("/qna/question/{memberId}")
    public String createQuestion(@PathVariable("memberId") Long memberId, @ModelAttribute("QuestionForm") QuestionForm form) {
        qnaService.question(memberId, form.getQuestion());
        return "redirect:/";
    }

    @GetMapping("/qna")
    public String viewQna(@SessionAttribute("loginMember")Member member, Model model) {
        List<QnA> qnaList = qnaService.findAll();
        model.addAttribute("member", member);
        model.addAttribute("qnaList", qnaList);
        return "/test/qnaList";
    }

    @GetMapping("/qna/{qnaId}")
    public String answerView(@PathVariable("qnaId")Long qnaId, Model model) {
        model.addAttribute("QuestionForm", new QuestionForm(qnaId));
        return "/test/answer";
    }

    @PostMapping("/qna/{qnaId}")
    public String answer(@PathVariable("qnaId")Long qnaId,@ModelAttribute("QuestionForm") QuestionForm form) {
        qnaService.answer(qnaId, form.getAnswer());
        return "redirect:/";
    }
}
