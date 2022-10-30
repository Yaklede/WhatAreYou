package com.WhatAreYou.WhatAreYou.repository.chatting;

import com.WhatAreYou.WhatAreYou.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
