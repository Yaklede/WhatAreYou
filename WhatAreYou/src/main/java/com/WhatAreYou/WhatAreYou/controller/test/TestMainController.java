package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.PostConstruct;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestMainController {



    @GetMapping("")
    public String MainTestPage(){
        return "/test/MainPage";
    }

    @GetMapping("/member")
    public String MemberTestPage() {
        return "/test/member/TestMainPage";
    }

    @GetMapping("/board")
    public String boardTestPage(@SessionAttribute(name = "loginMember",required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/test/member";
        }
        model.addAttribute("loginMember", loginMember);
        return "/test/board/BoardTestPage";
    }
}
