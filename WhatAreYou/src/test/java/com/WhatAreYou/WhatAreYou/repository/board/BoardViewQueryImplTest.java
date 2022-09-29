package com.WhatAreYou.WhatAreYou.repository.board;

import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardViewQueryImplTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Test
    public void fetchJoin() throws Exception {
        //given
        boardRepository.findCommentFetchByBoardId(1L);
        //when

        //then

    }

    @Test
    public void fetchJoinService() throws Exception {
        //given
        boardService.findCommentFetchByBoardId(1L);
        //when

        //then

    }
}