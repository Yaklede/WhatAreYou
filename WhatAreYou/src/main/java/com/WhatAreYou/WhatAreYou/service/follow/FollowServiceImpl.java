package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.error.ERROR;
import com.WhatAreYou.WhatAreYou.repository.follow.FollowRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Follow> followList(Long loginId, Long pageMemberId) {
        return followRepository.findAll();
    }

    @Override
    public Long follow(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new IllegalArgumentException(ERROR.member));
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new IllegalArgumentException(ERROR.member));
        return followRepository.mFollow(fromMember, toMember);
    }

    @Override
    public Long unfollow(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId).orElseThrow(() -> new IllegalArgumentException(ERROR.member));
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(() -> new IllegalArgumentException(ERROR.member));
        return followRepository.mUnFollow(fromMember,toMember);
    }
}
