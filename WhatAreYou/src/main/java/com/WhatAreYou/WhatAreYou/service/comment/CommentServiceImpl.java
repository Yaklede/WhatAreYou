package com.WhatAreYou.WhatAreYou.service.comment;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.CommentUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.CommentNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.comment.CommentRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override
    public Long create(Long memberId, Long boardId ,String comment) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

        Comment commentBuild= Comment.builder()
                .board(findBoard)
                .member(findMember)
                .comment(comment)
                .build();

        commentRepository.save(commentBuild);
        return commentBuild.getId();
    }

    @Override
    public void update(Long commentId , CommentUpdateForm updateForm) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        comment.changeComment(updateForm.getComment());
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId);
    }

    @Override
    public List<Comment> findByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
}
