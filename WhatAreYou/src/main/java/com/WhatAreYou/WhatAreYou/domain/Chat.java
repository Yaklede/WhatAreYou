package com.WhatAreYou.WhatAreYou.domain;

import com.WhatAreYou.WhatAreYou.dto.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String writer;
    private String message;

    @Builder
    public Chat(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.writer = chatMessage.getWriter();
        this.message = chatMessage.getMessage();
    }
}
