package com.WhatAreYou.WhatAreYou.repository.chatting;

import com.WhatAreYou.WhatAreYou.domain.Chat;
import com.WhatAreYou.WhatAreYou.dto.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<Chat,Long> {
}

