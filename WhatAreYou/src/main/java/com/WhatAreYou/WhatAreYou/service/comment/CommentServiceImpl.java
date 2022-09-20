package com.WhatAreYou.WhatAreYou.service.comment;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.CommentUpdateForm;
import com.WhatAreYou.WhatAreYou.error.ERROR;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.comment.CommentRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
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
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException(ERROR.board));
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException(ERROR.member));

        Comment commentBuild= Comment.builder()
                .board(findBoard)
                .member(findMember)
                .comment(comment)
                .build();

        findBoard.addComment(commentBuild);
        commentRepository.save(commentBuild);
        return commentBuild.getId();
    }

    @Override
    public void update(Long commentId , CommentUpdateForm updateForm) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(ERROR.comment));
        comment.setComment(updateForm.getComment());
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(ERROR.comment));
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
}
