package com.WhatAreYou.WhatAreYou.service.comment;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.CommentUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.CommentNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.comment.CommentRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class CommentServiceImplTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    public void init() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    public void comment() throws Exception {
        //given
        Member member = Member.builder()
                .email("e")
                .age(1)
                .nickName("a")
                .password("a")
                .loginId("a")
                .build();
        memberService.join(member);
        Board board = Board.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();
        boardService.create(board);
        //when
        Long createCommentId = commentService.create(member.getId(), board.getId(), "댓글입니다.");
        Comment comment = commentRepository.findById(createCommentId).get();
        //then
        org.assertj.core.api.Assertions.assertThat(comment.getComment()).isEqualTo("댓글입니다.");
        commentService.delete(createCommentId);

        CommentUpdateForm form = new CommentUpdateForm();
        form.setComment("a");
        org.junit.jupiter.api.Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.update(createCommentId, form));
    }

    @Test
    public void update() throws Exception {
        //given
        Member member = Member.builder()
                .email("e")
                .age(1)
                .nickName("a")
                .password("a")
                .loginId("a")
                .build();
        memberService.join(member);
        Board board = Board.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();
        boardService.create(board);
        //when
        Long createCommentId = commentService.create(member.getId(),board.getId(), "댓글입니다.");

        Comment comment = commentRepository.findById(createCommentId).get();
        Assertions.assertThat(comment.getComment()).isEqualTo("댓글입니다.");
        CommentUpdateForm form = new CommentUpdateForm();
        form.setComment("수정된 다른 댓글입니다.");
        commentService.update(createCommentId, form);

        Comment updateComment = commentRepository.findById(createCommentId).get();
        Assertions.assertThat(updateComment.getComment()).isEqualTo("수정된 다른 댓글입니다.");

    }
    @Test
    public void listComment() throws Exception {
        //given
        Member member = Member.builder()
                .email("e")
                .age(1)
                .nickName("a")
                .password("a")
                .loginId("a")
                .build();
        memberService.join(member);
        Member member2 = Member.builder()
                .email("e")
                .age(1)
                .nickName("b")
                .password("b")
                .loginId("b")
                .build();
        memberService.join(member2);
        Board board = Board.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();
        boardService.create(board);
        //when
        commentService.create(member.getId(), board.getId(), "첫번째 댓글");
        commentService.create(member2.getId(), board.getId(), "두번째 댓글");
        //then

        Comment member2Comment = commentService.findByMemberId(member2.getId());
        Assertions.assertThat(board.getComments().size()).isEqualTo(2);
        Assertions.assertThat(member2Comment.getMember()).isEqualTo(member2);
        Assertions.assertThat(member2Comment.getComment()).isEqualTo("두번째 댓글");
    }

}