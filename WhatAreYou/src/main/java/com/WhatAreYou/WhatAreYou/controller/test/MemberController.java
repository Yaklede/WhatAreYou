package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.JoinForm;
import com.WhatAreYou.WhatAreYou.dto.form.member.LoginForm;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberDeleteForm;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.NotEnoughStockException;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 30; i++) {
            Member member = Member.builder()
                    .nickName("닉네임" + i)
                    .age(i)
                    .email("EmailNumber" + i + "@gmail.com")
                    .password("pass" + i)
                    .loginId("loginId" + i)
                    .build();
            memberService.join(member);
        }
    }

    @GetMapping("/join")
    public String GetJoin(@ModelAttribute("joinForm") JoinForm joinForm) {
        return "/test/member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("joinForm") JoinForm joinForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/test/member/join";
        }
        try {
            Member member = Member.builder()
                    .loginId(joinForm.getLoginId())
                    .password(joinForm.getPassword())
                    .email(joinForm.getEmail())
                    .age(joinForm.getAge())
                    .nickName(joinForm.getNickName())
                    .build();
            memberService.join(member);
        } catch (NotEnoughStockException e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "/test/member/join";
        }
        return "redirect:/test/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "/test/member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/test/member/login";
        }
        Member loginMember = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "/test/member/login";
        }
        //로그인 성공
        log.info("loginMember {}",loginMember.getLoginId());
        HttpSession session = request.getSession();
        session.setAttribute("loginMember",loginMember);

        return "redirect:/test/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/test/";
    }


    /**
     * 어드민 페이지에서 사용 예정
     * 어드민만 get 으로 모든 사용자 조회 후 수정 가능
     * 일반 유저는 post 요청으로 update , delete 가능
     */
    @GetMapping("/delete")
    public String deleteForm(Model model) {
        List<Member> findMembers = memberService.findAll();
        List<MemberDeleteForm> members = findMembers.stream().map(member -> new MemberDeleteForm(member)).collect(Collectors.toList());
        model.addAttribute("members", members);
        return "/test/member/delete";
    }

    @GetMapping("/updateList")
    public String updateList(Model model) {
        List<Member> findMember = memberService.findAll();
        List<MemberUpdateForm> members = findMember.stream().map(member -> new MemberUpdateForm(member)).collect(Collectors.toList());
        model.addAttribute("members", members);
        return "/test/member/updateList";
    }

    @GetMapping("/update/{memberId}")
    public String updateForm(@PathVariable("memberId")Long memberId, Model model) {
        Member findMember = memberService.findByOne(memberId);
        MemberUpdateForm updateForm = new MemberUpdateForm(findMember);
        model.addAttribute("updateForm", updateForm);
        return "test/member/update";
    }
    @PostMapping("/delete/{memberId}")
    public String delete(@PathVariable("memberId") Long memberId) {
        Member findMember = memberService.findByOne(memberId);
        memberService.delete(findMember);
        return "redirect:/test/";
    }

    @PostMapping("/update/{memberId}")
    public String update(@PathVariable("memberId")Long memberId, @ModelAttribute("updateForm") MemberUpdateForm updateForm) {
        memberService.update(memberId,updateForm);
        return "redirect:/test/";
    }
}
