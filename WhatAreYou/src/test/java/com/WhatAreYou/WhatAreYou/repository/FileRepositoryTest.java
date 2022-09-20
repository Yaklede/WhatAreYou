package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.service.BoardService;
import com.WhatAreYou.WhatAreYou.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FileRepositoryTest {
    @Autowired
    FileRepository fileRepository;

    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;
    @Test
    public void findByBoardId() throws Exception {
        //given
        Member member = Member.builder()
                .email("testEmail")
                .age(2)
                .nickName("testNickName")
                .password("testPass")
                .loginId("testLogin")
                .build();
        memberService.join(member);

        Board board = Board.builder()
                .content("testContent")
                .member(member)
                .title("testTitle")
                .build();
        boardService.create(board);
        //when
        fileRepository.findByBoardId(board.getId());
        //then

    }
}