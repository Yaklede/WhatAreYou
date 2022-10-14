package com.WhatAreYou.WhatAreYou.repository.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CustomFollowRepository {

    Long mFollow(Member fromMember, Member toMember);
    @Modifying
    Long mUnFollow(Member fromMember, Member toMember);

    Long mFollowState(Member principalMember, Member member);

    Long mFollowingCount(Member member);

    Long mFollowerCount(Member member);
    //int 0 = false (팔로우 안됌) , int 1 = ture(팔로우됌);

    List<Follow> mFollowList(Member toMember);

    Page<Follow> findPageAll(Pageable pageable);

}
