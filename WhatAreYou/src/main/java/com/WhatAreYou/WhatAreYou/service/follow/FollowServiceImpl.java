package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.exception.FollowMemberCanNotSameException;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.follow.FollowRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Follow> followList(String loginId) {
        return followRepository.findAllByLoginId(loginId);
    }
    @Transactional
    @Override
    public Long follow(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new MemberNotFoundException());
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new MemberNotFoundException());
        if (fromMember == toMember) {
            throw new FollowMemberCanNotSameException("팔로우 회원은 같을 수 없습니다.");
        }

        return followRepository.mFollow(fromMember, toMember);
    }

    @Transactional
    @Override
    public Long unfollow(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new MemberNotFoundException());
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new MemberNotFoundException());
        return followRepository.mUnFollow(fromMember,toMember);
    }

    @Override
    public Long followState(Long loginId, Long pageMemberId) {
        Member loginMember = memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException());
        Member pageMember = memberRepository.findById(pageMemberId).orElseThrow(() -> new MemberNotFoundException());
        return followRepository.mFollowState(loginMember, pageMember);
        // 0 : false , 1 : true
    }
}