package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Member;
import org.springframework.data.jpa.repository.Modifying;

public interface CustomFollowRepository {

    @Modifying
    int mUnFollow(Member fromMember, Member toMember);

    int mFollowState(Member principalMember, Member member);

    int mFollowCount(Member member);
    //int 0 = false (팔로우 안됌) , int 1 = ture(팔로우됌);
}
