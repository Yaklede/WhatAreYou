package com.WhatAreYou.WhatAreYou.service.member;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.NotEnoughStockException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> findPageAll(Pageable pageable) {
        return memberRepository.findPageAll(pageable);
    }

    @Override
    public Page<Member> findRankingAll(Pageable pageable) {
        return memberRepository.findRankingAll(pageable);
    }

    @Override
    public Member findByOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
    }

    @Override
    public Member findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).get(0);
        return member;
    }

    @Transactional
    @Override
    public Long join(Member member) {
        validateDuplicate(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicate(Member member) {
        List<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
        if (!findMember.isEmpty()) {
            throw new NotEnoughStockException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public Member login(String loginId, String password) {
        Optional<Member> findMember = memberRepository.findOptionalByLoginId(loginId);
        return findMember.filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }

    @Transactional
    @Override
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        memberRepository.delete(member);
    }

    @Transactional
    @Override
    public void update(Long memberId, MemberUpdateForm updateForm) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        validationUpdate(updateForm, member);

    }

    private void validationUpdate(MemberUpdateForm updateForm, Member member) {
        if (updateForm.getPassword().isEmpty() || updateForm.getPassword() == null) {
            updateForm.setPassword(member.getPassword());
            member.updateMember(updateForm);
        } else if (updateForm.getEmail().isEmpty() || updateForm.getEmail() == null) {
            updateForm.setEmail(member.getEmail());
            member.updateMember(updateForm);
        } else if (updateForm.getNickName().isEmpty() || updateForm.getNickName() == null) {
            updateForm.setNickName(member.getNickName());
            member.updateMember(updateForm);
        } else member.updateMember(updateForm);
    }
}
