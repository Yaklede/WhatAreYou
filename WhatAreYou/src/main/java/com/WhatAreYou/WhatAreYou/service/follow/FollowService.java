package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    public List<Follow> followList(Long loginId, Long pageMemberId);

    public Long follow(Long fromMemberId, Long toMemberId);

    public Long unfollow(Long fromMemberId, Long toMemberId);
}
