package com.WhatAreYou.WhatAreYou.service.chatting;

import com.WhatAreYou.WhatAreYou.domain.ChatRoom;
import com.WhatAreYou.WhatAreYou.repository.chatting.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Override
    public ChatRoom findById(Long chatId) {
        return chatRoomRepository.findById(chatId).get();
    }

    @Override
    public Long create(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
        return chatRoom.getRoomId();
    }
}
