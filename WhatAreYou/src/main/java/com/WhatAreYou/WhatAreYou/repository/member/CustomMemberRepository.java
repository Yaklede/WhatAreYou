package com.WhatAreYou.WhatAreYou.repository.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMemberRepository {
    public Page<Member> findPageAll(Pageable pageable);

    public Page<Member> findRankingAll(Pageable pageable);
}
