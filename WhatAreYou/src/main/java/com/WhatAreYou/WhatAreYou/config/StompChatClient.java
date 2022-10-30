package com.WhatAreYou.WhatAreYou.config;

import com.WhatAreYou.WhatAreYou.domain.Chat;
import com.WhatAreYou.WhatAreYou.dto.ChatMessage;
import com.WhatAreYou.WhatAreYou.repository.chatting.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatClient {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository messageRepository;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage chatMessage) {
        System.out.println("연결성공");
        chatMessage.setMessage(chatMessage.getWriter() + "님이 채팅방에 참여하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/sub/chat/room/"+chatMessage.getRoomId(),chatMessage);
        Chat chat = Chat.builder().chatMessage(chatMessage).build();
        messageRepository.save(chat);
    }
}
