package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    public List<Follow> followList(Long loginId, Long pageMemberId);

    public int follow(Long fromMemberId, Long toMemberId);

    public int unfollow(Long fromMemberId, Long toMemberId);
}
