package com.WhatAreYou.WhatAreYou.service.comment;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.comment.CommentUpdateForm;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        Long createCommentId = commentService.create(member.getId(), board.getId(), "???????????????.");
        Comment comment = commentRepository.findById(createCommentId).get();
        //then
        org.assertj.core.api.Assertions.assertThat(comment.getComment()).isEqualTo("???????????????.");
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
        Long createCommentId = commentService.create(member.getId(),board.getId(), "???????????????.");

        Comment comment = commentRepository.findById(createCommentId).get();
        Assertions.assertThat(comment.getComment()).isEqualTo("???????????????.");
        CommentUpdateForm form = new CommentUpdateForm();
        form.setComment("????????? ?????? ???????????????.");
        commentService.update(createCommentId, form);

        Comment updateComment = commentRepository.findById(createCommentId).get();
        Assertions.assertThat(updateComment.getComment()).isEqualTo("????????? ?????? ???????????????.");

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
        commentService.create(member.getId(), board.getId(), "????????? ??????");
        commentService.create(member2.getId(), board.getId(), "????????? ??????");
        //then

        List<Comment> byMemberId = commentService.findByMemberId(member2.getId());
        Comment member2Comment = (Comment) byMemberId;
        Assertions.assertThat(board.getComments().size()).isEqualTo(2);
        Assertions.assertThat(member2Comment.getMember()).isEqualTo(member2);
        Assertions.assertThat(member2Comment.getComment()).isEqualTo("????????? ??????");
    }

}