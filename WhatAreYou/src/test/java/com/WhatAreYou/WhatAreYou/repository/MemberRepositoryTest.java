package com.WhatAreYou.WhatAreYou.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findOptionalByLoginId() throws Exception {
        //given
        memberRepository.findOptionalByLoginId("a");
        //when

        //then

    }
}