package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    public List<Follow> followList(Member toMember);

    public Long followCount(Member fromMember);

    public Long follow(Long fromMemberId, Long toMemberId);

    public Long unfollow(Long fromMemberId, Long toMemberId);

    public Long followState(Long pageMemberId, Long loginId);

}
