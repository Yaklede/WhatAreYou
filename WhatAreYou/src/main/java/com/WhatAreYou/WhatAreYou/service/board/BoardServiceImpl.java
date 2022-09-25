package com.WhatAreYou.WhatAreYou.service.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        boardRepository.delete(findBoard);
    }

    @Transactional
    @Override
    public void update(Long boardId,BoardUpdateForm updateForm) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        validationUpdateForm(updateForm, findBoard);
    }

    private void validationUpdateForm(BoardUpdateForm updateForm, Board findBoard) {
        if (updateForm.getTitle() == null || updateForm.getContent() == null) {
            if (updateForm.getTitle() == null) {
                BoardUpdateForm updateContent = BoardUpdateForm.builder()
                        .title(findBoard.getTitle())
                        .content(updateForm.getContent())
                        .build();
                findBoard.changeBoard(updateContent);
            } else {
                BoardUpdateForm updateTitle = BoardUpdateForm.builder()
                        .content(findBoard.getContent())
                        .title(updateForm.getTitle())
                        .build();
                findBoard.changeBoard(updateTitle);
            }
        } else {
            findBoard.changeBoard(updateForm);
        }
    }


    @Override
    public Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
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
