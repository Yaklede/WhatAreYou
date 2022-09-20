package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;

    @BeforeEach
    public void init() {
        Member member = Member.builder()
                .loginId("test")
                .password("test")
                .nickName("test")
                .age(2)
                .email("test@naver.com")
                .build();

        memberService.join(member);
    }

    @Test
    public void boardCreate() throws Exception {
        //given
        Member findMember = memberService.findByLoginId("test");
        //when
        Board board = Board.builder()
                .member(findMember)
                .content("content")
                .title("title")
                .build();
        Long createBoardId = boardService.create(board);
        //then
        Board findBoard = boardService.findByBoardId(createBoardId);

        Assertions.assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
    }



}