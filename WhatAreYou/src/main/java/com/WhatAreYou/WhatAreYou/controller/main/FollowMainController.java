package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.service.follow.FollowService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FollowMainController {

    private final FollowService followService;

    @PostMapping("/follow/{fromMemberId}/{toMemberId}")
    public String follow(@PathVariable("fromMemberId") Long fromMemberId, @PathVariable("toMemberId") Long toMemberId) {
        followService.follow(fromMemberId, toMemberId);
        return "redirect:/profile/{fromMemberId}/{toMemberId}";
    }

    @PostMapping("/unfollow/{fromMemberId}/{toMemberId}")
    public String unfollow(@PathVariable("fromMemberId") Long fromMemberId, @PathVariable("toMemberId") Long toMemberId) {
        followService.unfollow(fromMemberId, toMemberId);
        return "redirect:/profile/{fromMemberId}/{toMemberId}";
    }
}
