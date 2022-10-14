package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void findAllByMemberId() throws Exception {
        //given
        Member findMember = memberRepository.findOptionalByLoginId("a").get();
        boardRepository.findAllByMemberId(findMember.getId());
        //when

        //then
    }
    @Test
    public void countByMember() throws Exception {
        //given
        Long count = boardRepository.countByMemberId(1L);
        Assertions.assertThat(count).isEqualTo(0);
        //when

        //then

    }
}