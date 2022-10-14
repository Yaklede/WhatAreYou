package com.WhatAreYou.WhatAreYou.service.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.HashTag;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.file.FileRepository;
import com.WhatAreYou.WhatAreYou.repository.hashtag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final HashTagRepository hashTagRepository;

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
    public void deleteAllByMemberId(Long memberId) {
        List<Board> boards = boardRepository.findAllByMemberId(memberId);
        boards.stream().map(board -> hashTagRepository.findByBoardId(board.getId())).forEach(hashTagRepository::delete);
        boardRepository.deleteAll(boards);
    }

    @Transactional
    @Override
    public void update(Long boardId,BoardUpdateForm updateForm) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        validationUpdateForm(updateForm, findBoard);
    }

    private void validationUpdateForm(BoardUpdateForm updateForm, Board findBoard) {
        if (updateForm.getTitle() == null || updateForm.getContent() == null || updateForm.getFile() == null || updateForm.getHashTag() == null) {
            if (updateForm.getTitle() == null) {
                updateForm.setTitle(findBoard.getTitle());
            } else if (updateForm.getContent() == null) {
                updateForm.setContent(findBoard.getContent());
            } else if (updateForm.getFile() == null) {
                updateForm.setFileId(findBoard.getFileEntity().getId());
            } else if(updateForm.getHashTag() == null) {
                updateForm.setHashTag(findBoard.getHashTags().getTag());
            }
        }  else if(updateForm.getTitle().isEmpty() || updateForm.getContent().isEmpty() || updateForm.getFile().isEmpty() || updateForm.getHashTag().isEmpty()) {
            if (updateForm.getTitle().isEmpty()) {
                updateForm.setTitle(findBoard.getTitle());
            } else if (updateForm.getContent().isEmpty()) {
                updateForm.setContent(findBoard.getContent());
            } else if (updateForm.getFile().isEmpty()) {
                updateForm.setFileId(findBoard.getFileEntity().getId());
            } else if (updateForm.getHashTag().isEmpty()) {
                updateForm.setHashTag(findBoard.getHashTags().getTag());
            }
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
    public Page<Board> findPageAll(Pageable pageable) {
        return boardRepository.findPageAll(pageable);
    }

    @Override
    public Page<Board> findSearchPageAll(BoardSearchCondition condition, Pageable pageable) {
        return boardRepository.findSearchPageAll(condition,pageable);
    }

    @Override
    public Page<Board> findSearchComplexPageAll(BoardSearchCondition condition, Pageable pageable) {
        return boardRepository.findSearchComplexPageAll(condition,pageable);
    }

    @Override
    public List<Board> findAllByMemberId(Long memberId) {
        return boardRepository.findAllByMemberId(memberId);
    }

    @Override
    public Board findCommentFetchByBoardId(Long boardId) {
        return boardRepository.findCommentFetchByBoardId(boardId);
    }

    @Override
    public Long boardCountByMemberId(Long memberId) {
        return boardRepository.countByMemberId(memberId);
    }


}
