package com.WhatAreYou.WhatAreYou.service.like;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeServiceImplTest {
    @Autowired
    LikeService likeService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        //작성자는 member 0번
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .loginId("loginId" + i)
                    .password("pass" + i)
                    .email("email" + i)
                    .age(i)
                    .nickName("nick" + i)
                    .build();
            memberRepository.save(member);
        }
        Member writer = memberRepository.findOptionalByLoginId("loginId0").get();
        Board board = Board.builder()
                .member(writer)
                .content("content")
                .title("title")
                .build();
        boardRepository.save(board);
    }

    @Test
    public void likes() throws Exception {
        //given
        List<Member> all = memberRepository.findAll();
        Board board = boardRepository.findAll().get(0);
        //when
        for (Member member : all) {
            if (member.getLoginId().equals("loginId0")) {
                continue;
            }
            likeService.like(board.getId(), member.getId());
        }
        //then
        Long likeCount = likeService.BoardLikeCount(board.getId());
        Assertions.assertThat(likeCount).isEqualTo(9);
    }

    @Test
    public void unlike() throws Exception {
        //given
        List<Member> all = memberRepository.findAll();
        Board board = boardRepository.findAll().get(0);
        //when
        for (Member member : all) {
            if (member.getLoginId().equals("loginId0")) {
                continue;
            }
            likeService.like(board.getId(), member.getId());
        }
        Long likeCount = likeService.BoardLikeCount(board.getId());
        Assertions.assertThat(likeCount).isEqualTo(9);
        Member unLikeMember = memberRepository.findOptionalByLoginId("loginId1").get();
        Member LikeMember = memberRepository.findOptionalByLoginId("loginId2").get();
        //then
        likeService.unLike(board.getId(), unLikeMember.getId());
        Long discountOneLike = likeService.BoardLikeCount(board.getId());
        Assertions.assertThat(discountOneLike).isEqualTo(8);

    }
    @Test
    public void likeState() throws Exception {
        //given
        List<Member> all = memberRepository.findAll();
        Board board = boardRepository.findAll().get(0);
        //when
        for (Member member : all) {
            if (member.getLoginId().equals("loginId0")) {
                continue;
            }
            likeService.like(board.getId(), member.getId());
        }
        //when
        Member unLikeMember = memberRepository.findOptionalByLoginId("loginId1").get();
        Member LikeMember = memberRepository.findOptionalByLoginId("loginId2").get();
        likeService.unLike(board.getId(), unLikeMember.getId());
        //then
        Long unLikeMemberState = likeService.likeState(board.getId(), unLikeMember.getId());
        Long LikeMemberState = likeService.likeState(board.getId(), LikeMember.getId());
        Assertions.assertThat(unLikeMemberState).isEqualTo(0L);
        Assertions.assertThat(LikeMemberState).isEqualTo(1L);
    }
}