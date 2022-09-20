package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.repository.follow.FollowRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class FollowRepositoryTest {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void init() {
        memberRepository.save(new Member("a", "a", "a", "a", 10));
        memberRepository.save(new Member("b", "a", "b", "a", 10));
        memberRepository.save(new Member("c", "a", "c", "a", 10));
    }

    @Test
    public void follow() throws Exception {
        Member memberA = memberRepository.findOptionalByLoginId("a").get();
        Member memberB = memberRepository.findOptionalByLoginId("b").get();

        //given
        Follow follow = new Follow(memberA, memberB);
        followRepository.save(follow);
        //when
        //then

    }
    @Test
    public void unFollow() throws Exception {
        Member memberA = memberRepository.findOptionalByLoginId("a").get();
        Member memberB = memberRepository.findOptionalByLoginId("b").get();
        //given
        followRepository.mUnFollow(memberA, memberB);
        //when
        //then
    }
    @Test
    public void followCount() throws Exception {
        //given
        Member memberA = memberRepository.findOptionalByLoginId("a").get();
        Member memberB = memberRepository.findOptionalByLoginId("b").get();
        Member memberC = memberRepository.findOptionalByLoginId("c").get();

        Follow follow = new Follow(memberA, memberB);
        followRepository.save(follow);
        Follow follow1 = new Follow(memberA, memberC);
        followRepository.save(follow1);

        em.flush();
        em.clear();
        //when
        Long count = followRepository.mFollowCount(memberA);
        //then
        System.out.println("count = " + count);

        Assertions.assertThat(count).isEqualTo(2);

    }
    @Test
    public void followState() throws Exception {
        //given
        Member memberA = memberRepository.findOptionalByLoginId("a").get();
        Member memberB = memberRepository.findOptionalByLoginId("b").get();
        Member memberC = memberRepository.findOptionalByLoginId("c").get();

        Follow follow = new Follow(memberA, memberB);
        followRepository.save(follow);
        em.flush();
        em.clear();

        Long followTrue = followRepository.mFollowState(memberA, memberB);
        Long followFalse = followRepository.mFollowState(memberA, memberC);
        //when

        System.out.println("followFalse = " + followFalse);
        System.out.println("followTrue = " + followTrue);
        //then

    }
}