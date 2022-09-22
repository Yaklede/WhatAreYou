package com.WhatAreYou.WhatAreYou.controller.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestMainController {

    @GetMapping("/")
    public String MainTestPage(){
        return "/test/MainPage";
    }

    @GetMapping("/member")
    public String MemberTestPage() {
        return "/test/member/TestMainPage";
    }
}
