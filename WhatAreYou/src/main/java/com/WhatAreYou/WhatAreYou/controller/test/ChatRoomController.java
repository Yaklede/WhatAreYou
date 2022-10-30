package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.ChatRoom;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.chatting.ChatMessageRepository;
import com.WhatAreYou.WhatAreYou.repository.chatting.ChatRoomRepository;
import com.WhatAreYou.WhatAreYou.service.chatting.ChatRoomService;
import com.WhatAreYou.WhatAreYou.service.cusmission.CusMissionService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository messageRepository;
    private final CusMissionService cusMissionService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public String rooms(Model model){
        model.addAttribute("list", chatRoomService.findAll());
        return "/chat/rooms";
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name){
        ChatRoom build = ChatRoom.builder().name(name).build();
        chatRoomRepository.save(build);
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(@RequestParam(value = "roomId") Long roomId,@SessionAttribute("loginMember")Member loginMember,Model model){
        model.addAttribute("room", chatRoomService.findById(roomId));
        model.addAttribute("member", loginMember);
        model.addAttribute("messageList", messageRepository.findAll());
        return "/chat/room";
    }

    @PostMapping("/room/{memberNickName}")
    public String createTest(@PathVariable String memberNickName){
        log.info("test ======================");
        ChatRoom chatRoom = ChatRoom.builder().name(memberNickName).build();
        chatRoomRepository.save(chatRoom);
        return "redirect:/chat/rooms";
    }

 }
