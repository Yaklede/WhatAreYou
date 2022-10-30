package com.WhatAreYou.WhatAreYou.domain;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String name;

    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();
    @Builder
    public ChatRoom(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}
