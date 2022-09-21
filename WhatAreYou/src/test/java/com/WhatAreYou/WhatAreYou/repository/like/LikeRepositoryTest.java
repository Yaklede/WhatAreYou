package com.WhatAreYou.WhatAreYou.repository.like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryTest {
    @Autowired
    LikeRepository likeRepository;

    @Test
    public void findAllByBoardId() throws Exception {
        //given
        likeRepository.findAllByBoardId(1L);
        //when

        //then

    }
}