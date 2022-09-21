package com.WhatAreYou.WhatAreYou.service.like;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Like;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.like.LikeRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public Long like(Long fromBoardId, Long toMemberId) {
        Board fromBoard = boardRepository.findById(fromBoardId).orElseThrow(() -> new BoardNotFoundException());
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new MemberNotFoundException());
        return likeRepository.mLike(fromBoard, toMember);
    }
    @Transactional
    @Override
    public Long unLike(Long fromBoardId, Long toMemberId) {
        Board fromBoard = boardRepository.findById(fromBoardId).orElseThrow(() -> new BoardNotFoundException());
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new MemberNotFoundException());
        return likeRepository.mUnLike(fromBoard,toMember);
    }

    @Override
    public Long likeState(Long pageBoardId, Long loginMemberId) {
        Board pageBoard = boardRepository.findById(pageBoardId).orElseThrow(() -> new BoardNotFoundException());
        Member loginMember  = memberRepository.findById(loginMemberId).orElseThrow(() -> new MemberNotFoundException());
        return likeRepository.mLikeState(pageBoard,loginMember);
        // 0: 좋아요 안 누름
        // 1: 좋아요 누름
    }

    @Override
    public List<Like> likeList(Long fromBoardId) {
        return likeRepository.findAllByBoardId(fromBoardId);
    }

    @Override
    public Long MemberLikeCount(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        return likeRepository.mLikeCount(member);
    }

    @Override
    public Long BoardLikeCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        return likeRepository.mBoardLikeCount(board);
    }
}
