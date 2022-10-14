package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    public List<Follow> followList(Member toMember);

    public Long followerCount(Member fromMember);

    public Long followingCount(Member toMember);

    public Long follow(Long fromMemberId, Long toMemberId);

    public Long unfollow(Long fromMemberId, Long toMemberId);

    public Long followState(Long pageMemberId, Long loginId);

    public void deleteAll(Long memberId);

    public Page<Follow> findPageAll(Pageable pageable);

}
