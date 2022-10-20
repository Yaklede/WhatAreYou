package com.WhatAreYou.WhatAreYou.service.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.HashTag;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.file.FileRepository;
import com.WhatAreYou.WhatAreYou.repository.hashtag.HashTagRepository;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final HashTagRepository hashTagRepository;
    private final FileService fileService;

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
        validationUpdate(updateForm, findBoard);
    }

    private void validationUpdate(BoardUpdateForm updateForm, Board findBoard) {
        try {
            if (updateForm.getFile() == null || updateForm.getFile().isEmpty()) {
                FileEntity updateFile = fileService.findByBoardId(findBoard.getId());
                updateForm.setFileEntity(updateFile);
                findBoard.updateBoard(updateForm);
                HashTag tag = hashTagRepository.findByBoardId(findBoard.getId());
                tag.tagUpdate(updateForm.getHashTag());
            } else if (updateForm.getFile() != null) {
                FileEntity updateFile = fileService.findByBoardId(findBoard.getId());
                log.info("file = {}", updateFile.getId());
                Long updateFileId = fileService.updateFile(updateFile, updateForm.getFile());
                updateForm.setFileEntity(fileService.findByOne(updateFileId));
                HashTag tag = hashTagRepository.findByBoardId(findBoard.getId());
                tag.tagUpdate(updateForm.getHashTag());
                findBoard.updateBoard(updateForm);
            }
            HashTag tag = hashTagRepository.findByBoardId(findBoard.getId());
            tag.tagUpdate(updateForm.getHashTag());
            findBoard.updateBoard(updateForm);
        } catch (IOException e) {
            e.printStackTrace();
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
    public Page<Board> findRankingAll(Pageable pageable) {
        return boardRepository.findRankingAll(pageable);
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
