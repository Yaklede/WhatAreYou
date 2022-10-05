package com.WhatAreYou.WhatAreYou.repository.board;

import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Test
    public void page() throws Exception {
        //given
        PageRequest request = PageRequest.of(0, 3);
        boardRepository.findPageAll(request);
        //when

        //then

    }
    @Test
    public void pageCondition() throws Exception {
        //given
        PageRequest request = PageRequest.of(0, 3);
        BoardSearchCondition boardSearchCondition = new BoardSearchCondition();
        boardSearchCondition.setTag("#태그1");
        boardSearchCondition.setTitle("제목");
        boardRepository.findSearchPageAll(boardSearchCondition, request);
        //when

        //then

    }
}