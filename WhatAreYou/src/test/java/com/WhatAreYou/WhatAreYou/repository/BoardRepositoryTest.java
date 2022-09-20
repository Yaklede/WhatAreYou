package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void init() {
        memberRepository.save(new Member("a", "b", "c", "d", 10));
    }

    @Test
    public void findAllByMemberId() throws Exception {
        //given
        Member findMember = memberRepository.findOptionalByLoginId("a").get();
        boardRepository.findAllByMemberId(findMember.getId());
        //when

        //then

    }
}