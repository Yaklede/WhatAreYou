package com.WhatAreYou.WhatAreYou.repository.comment;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void commentFetchBoard() throws Exception {
        //given
        commentRepository.findByBoardId(1L);
        //when

        //then

    }
}