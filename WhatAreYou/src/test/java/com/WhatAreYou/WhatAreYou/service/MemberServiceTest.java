package com.WhatAreYou.WhatAreYou.service;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.NotEnoughStockException;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;
    @Test
    public void findAll() throws Exception {
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);

        //given
        memberService.findAll();
        //when

        //then

    }

    @Test
    public void findByOne() throws Exception {
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);
        //given
        memberService.findByOne(member.getId());
        //when

        //then

    }

    @Test
    public void join() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);
        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        //then
        Assertions.assertThat(findMember.getLoginId()).isEqualTo(member.getLoginId());
    }

    @Test
    public void login() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);

        //when
        Member findMember = memberService.findByOne(member.getId());
        Member loginMember = memberService.login("a", "a");
        //then
        Assertions.assertThat(findMember).isEqualTo(loginMember);

        Member failLogin = memberService.login("b", "a");
        Assertions.assertThat(failLogin).isNull();
    }
    @Test
    public void delete() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);
        //when
        memberService.delete(member.getId());
        //then
        org.junit.jupiter.api.Assertions.assertThrows(MemberNotFoundException.class, () -> memberService.findByOne(member.getId()));
    }
    @Test
    public void update() throws Exception {
        //given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .email("a")
                .age(1)
                .nickName("a")
                .build();
        memberService.join(member);
        //when
        Member findMember = memberService.findByOne(member.getId());
        //then
        MemberUpdateForm updateForm = MemberUpdateForm.builder()
                .age(1)
                .email("b")
                .nickName("b")
                .password("b")
                .build();
        Assertions.assertThat(findMember.getNickName()).isEqualTo("a");
        memberService.update(findMember.getId(), updateForm);
        Member updateMember = memberService.findByOne(member.getId());
        Assertions.assertThat(updateMember.getNickName()).isEqualTo("b");
        Assertions.assertThat(findMember.getLoginId()).isEqualTo(updateMember.getLoginId());

    }

}