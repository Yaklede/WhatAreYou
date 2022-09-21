package com.WhatAreYou.WhatAreYou.service.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.exception.FollowMemberCanNotSameException;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceImplTest {
    @Autowired
    FollowService followService;

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    public void FollowListLog() throws Exception {
        //given
        ArrayList<Object> list = new ArrayList<>();
        //when
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .loginId("member" + i)
                    .password("pass" + i)
                    .nickName("nick" + i)
                    .age(i)
                    .email("email" + i)
                    .build();
            memberRepository.save(member);
        }
        //then
        Member member1 = memberRepository.findOptionalByLoginId("member1").get();
        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            if (member.getId() == member1.getId()) {
                continue;
            } else {
                followService.follow(member1.getId(), member.getId());
            }
        }
        List<Follow> follows = followService.followList(member1.getLoginId());
        for (Follow follow : follows) {
            System.out.println("follow = " + follow.getToMember().getLoginId());
        }
        followStateLog(list, member1, all);
        for (Object o : list) {
            System.out.println(o);
        }

        System.out.println("unFollow ==================================");
        list.clear();

        for (Member member : all) {
            if (member.getId() == member1.getId()) {
                continue;
            } else {
                followService.unfollow(member1.getId(), member.getId());
            }
        }
        followStateLog(list, member1, all);
        for (Object o : list) {
            System.out.println(o);
        }

    }

    private void followStateLog(ArrayList<Object> list, Member member1, List<Member> all) {
        for (Member member : all) {
            Long state = followService.followState(member1.getId(), member.getId());
            if (state == 0L) {
                list.add("팔로우 안됌");
            } else {
                list.add("팔로우 됌");
            }
        }
    }

    @Test
    public void FollowListException() throws Exception {
        //given
        //when
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .loginId("member" + i)
                    .password("pass" + i)
                    .nickName("nick" + i)
                    .age(i)
                    .email("email" + i)
                    .build();
            memberRepository.save(member);
        }
        //then
        Member fromMember = memberRepository.findOptionalByLoginId("member1").get();
        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            try {
                followService.follow(fromMember.getId(), member.getId());
            } catch (FollowMemberCanNotSameException e) {
                org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("팔로우 회원은 같을 수 없습니다.");
            }
        }
    }
}