package com.WhatAreYou.WhatAreYou.repository.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long>,CustomFollowRepository {

    @Query("select f from Follow f join fetch f.fromMember m where m.loginId = :loginId")
    public List<Follow> findAllByLoginId(@Param("loginId") String loginId);

    @Query("select f from Follow f join fetch f.fromMember m where m.id = :memberId")
    public List<Follow> findAllByFromMemberId(@Param("memberId") Long memberId);

    @Query("select f from Follow f join fetch f.toMember m where m.id = :memberId")
    public List<Follow> findAllByToMemberId(@Param("memberId") Long memberId);

}
