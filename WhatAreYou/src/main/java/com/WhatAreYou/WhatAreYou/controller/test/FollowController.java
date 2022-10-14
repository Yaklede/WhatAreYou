package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.LoginForm;
import com.WhatAreYou.WhatAreYou.exception.FollowMemberCanNotSameException;
import com.WhatAreYou.WhatAreYou.service.follow.FollowService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class FollowController {
    private final MemberService memberService;
    private final FollowService followService;


    @GetMapping("/member/followList")
    public String followList(@SessionAttribute(value = "loginMember",required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "redirect:/test/member";
        }
        List<Member> members = findFollows(loginMember);
        Long followerCount = followService.followerCount(loginMember);
        Long followingCount = followService.followingCount(loginMember);
        model.addAttribute("members", members);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);
        return "/test/member/followList";
    }

    private List<Member> findFollows(Member loginMember) {
        List<Member> members = memberService.findAll();
        for (Member member : members) {
            Long state = followService.followState(member.getId(), loginMember.getId());
            member.setFollowState(state);
        }
        return members;
    }

    @PostMapping("/member/follow/{memberId}")
    public String follow(@PathVariable("memberId") Long fromMember, @SessionAttribute(value = "loginMember",required = false) Member toMember) {
        followService.follow(fromMember, toMember.getId());
        Long state = followService.followState(fromMember, toMember.getId());
        log.info("Follow state = {}", state);
        return "redirect:/test/member";
    }

    @PostMapping("/member/unfollow/{memberId}")
    public String unfollow(@PathVariable("memberId") Long fromMember, @SessionAttribute(value = "loginMember",required = false) Member toMember) {
        followService.unfollow(fromMember, toMember.getId());
        Long state = followService.followState(fromMember, toMember.getId());
        log.info("Un Follow state = {}", state);
        return "redirect:/test/member";
    }

}
