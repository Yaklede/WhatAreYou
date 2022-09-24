package com.WhatAreYou.WhatAreYou.service.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface MemberService {
    public List<Member> findAll();
    public Member findByOne(Long memberId);
    public Member findByLoginId(String loginId);
    public Long join(Member member);
    public Member login(String loginId, String password);
    public void delete(Member member);
    //회원 닉네임, 패스워드, 나이, 이메일 변경가능
    public void update(Long memberId, MemberUpdateForm updateForm);

}
