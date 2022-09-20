package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.BoardUpdateForm;
import com.WhatAreYou.WhatAreYou.repository.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public Long create(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    @Override
    public void delete(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).orElse(null);
        boardRepository.delete(findBoard);
    }

    @Transactional
    @Override
    public void update(Long boardId,BoardUpdateForm updateForm) {
        Board findBoard = boardRepository.findById(boardId).orElse(null);
        boardUpdate(findBoard, updateForm);
    }

    @Transactional
    private void boardUpdate(Board findBoard, BoardUpdateForm updateForm) {
        findBoard.setContent(updateForm.getContent());
        findBoard.setTitle(updateForm.getTitle());
    }

    @Override
    public Board findByBoardId(Long boardId) {

        return boardRepository.findById(boardId).orElse(null);
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> findAllByMemberId(Long memberId) {
        return boardRepository.findAllByMemberId(memberId);
    }

}
