package com.WhatAreYou.WhatAreYou.service.chatting;

import com.WhatAreYou.WhatAreYou.domain.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatRoomService {
    public List<ChatRoom> findAll();

    public ChatRoom findById(Long chatId);

    public Long create(ChatRoom chatRoom);
}
