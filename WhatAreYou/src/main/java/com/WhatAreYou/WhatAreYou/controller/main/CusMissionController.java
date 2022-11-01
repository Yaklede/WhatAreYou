package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.domain.*;
import com.WhatAreYou.WhatAreYou.dto.form.cusMission.CusForm;
import com.WhatAreYou.WhatAreYou.dto.form.cusMission.CusSubmitForm;
import com.WhatAreYou.WhatAreYou.service.chatting.ChatRoomService;
import com.WhatAreYou.WhatAreYou.service.cusmission.CusMissionService;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CusMissionController {

    private final CusMissionService cusMissionService;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping("/CusMissionList")
    public String list(Model model) {
        model.addAttribute("cusMissionList", cusMissionService.findAll());
        return "/main/cusMission/cusMissionList";
    }

    @GetMapping("/CusMission/create")
    public String createView(@SessionAttribute("loginMember") Member loginMember, Model model) {
        CusForm cusForm = new CusForm();
        cusForm.setRequesterId(loginMember.getId());
        cusForm.setRequesterName(loginMember.getNickName());
        model.addAttribute("cusForm", cusForm);
        return "/main/cusMission/create";
    }

    @PostMapping("/CusMission/create/{requesterId}")
    public String create(@Valid @ModelAttribute("cusForm")CusForm cusForm,BindingResult bindingResult, @PathVariable("requesterId")Long memberId) {
        if (bindingResult.hasErrors()) {
            return "/main/cusMission/create";
        }

        CusMission cusMission = getCusMission(memberId, cusForm);
        cusMissionService.create(cusMission);
        return "redirect:/CusMissionList";
    }

    @GetMapping("/CusMission/detail/{cusId}")
    public String view(@PathVariable("cusId") Long cusId, @SessionAttribute("loginMember")Member loginMember, @ModelAttribute("form")CusSubmitForm form, Model model) {
        CusMission cusMission = cusMissionService.findById(cusId);
        ChatRoom chatRoom = chatRoomService.findById(cusMission.getRoom().getRoomId());
        model.addAttribute("cus", cusMission);
        model.addAttribute("member", loginMember);
        model.addAttribute("chatRoom", chatRoom);
        return "/main/cusMission/cusMission";
    }

    @PostMapping("/CusMission/accept/{cusId}/{memberId}")
    public String accept(@PathVariable("cusId") Long cusId, @PathVariable("memberId") Long memberId) {
        cusMissionService.accept(cusId,memberId);
        return "redirect:/CusMissionList";
    }
    @PostMapping("/CusMission/submit/{cusId}")
    public String submit(@PathVariable("cusId")Long cusId, @ModelAttribute("form")CusSubmitForm form) throws IOException {
        Long saveFile = fileService.saveFile(form.getFile());
        FileEntity file = fileService.findByOne(saveFile);
        cusMissionService.changeState(cusId,CusState.CHECK,file);
        return "redirect:/CusMission/detail/{cusId}";
    }
    @PostMapping("/CusMission/done/{cusId}")
    public String done(@PathVariable("cusId")Long cusId) {
        cusMissionService.changeState(cusId,CusState.DONE,fileService.findByCusId(cusId));
        return "redirect:/CusMission/detail/{cusId}";
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
