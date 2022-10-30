package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.ChatRoom;
import com.WhatAreYou.WhatAreYou.domain.CusMission;
import com.WhatAreYou.WhatAreYou.domain.CusState;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.cusMission.CusForm;
import com.WhatAreYou.WhatAreYou.repository.chatting.ChatRoomRepository;
import com.WhatAreYou.WhatAreYou.service.chatting.ChatRoomService;
import com.WhatAreYou.WhatAreYou.service.cusmission.CusMissionService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class TestCusMissionController {

    private final CusMissionService cusMissionService;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    @GetMapping("/CusMissionList")
    public String list(Model model) {
        model.addAttribute("cusMissionList", cusMissionService.findAll());
        return "/test/cusMission/List";
    }

    @GetMapping("/CusMission/create")
    public String createView(@SessionAttribute("loginMember") Member loginMember, Model model) {
        CusForm cusForm = new CusForm();
        cusForm.setRequesterId(loginMember.getId());
        cusForm.setRequesterName(loginMember.getNickName());
        model.addAttribute("cusForm", cusForm);
        return "/test/cusMission/create";
    }

    @PostMapping("/CusMission/create/{requesterId}")
    public String create(@PathVariable("requesterId")Long memberId,@ModelAttribute("cusForm")CusForm cusForm) {
        CusMission cusMission = getCusMission(memberId, cusForm);
        cusMissionService.create(cusMission);
        return "redirect:/test/CusMissionList";
    }

    @GetMapping("/CusMission/{cusId}")
    public String cusView(@PathVariable("cusId") Long cusId,@SessionAttribute("loginMember")Member loginMember,Model model) {
        CusMission cusMission = cusMissionService.findById(cusId);
        ChatRoom chatRoom = chatRoomService.findById(cusMission.getRoom().getRoomId());
        model.addAttribute("cus", cusMission);
        model.addAttribute("member", loginMember);
        model.addAttribute("chatRoom", chatRoom);
        return "/test/cusMission/view";
    }

    @PostMapping("/CusMission/accept/{cusId}/{memberId}")
    public String accept(@PathVariable("cusId") Long cusId, @PathVariable("memberId") Long memberId) {
        cusMissionService.accept(cusId,memberId);
        return "redirect:/test/CusMissionList";
    }


    private CusMission getCusMission(Long memberId, CusForm cusForm) {
        Member member = memberService.findByOne(memberId);
        ChatRoom chatRoom = ChatRoom.builder().name(member.getNickName()).build();
        chatRoomService.create(chatRoom);
        CusMission cusMission = CusMission.builder()
                .requester(member)
                .title(cusForm.getTitle())
                .content(cusForm.getContent())
                .price(cusForm.getPrice())
                .cusState(CusState.READY)
                .chatRoom(chatRoom)
                .build();
        return cusMission;
    }
}
