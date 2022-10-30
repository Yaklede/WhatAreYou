package com.WhatAreYou.WhatAreYou.service.cusmission;

import com.WhatAreYou.WhatAreYou.domain.CusMission;
import com.WhatAreYou.WhatAreYou.domain.CusState;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.exception.CusMissionNotFoundException;
import com.WhatAreYou.WhatAreYou.exception.MemberNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.cusmission.CusMissionRepository;
import com.WhatAreYou.WhatAreYou.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CusMissionServiceImpl implements CusMissionService {

    private final CusMissionRepository cusMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<CusMission> findAll() {
        return cusMissionRepository.findAll();
    }

    @Override
    public CusMission findById(Long cusId) {
        return cusMissionRepository.findById(cusId).orElseThrow(() -> new CusMissionNotFoundException());
    }

    @Transactional
    @Override
    public Long create(CusMission cusMission) {
        cusMissionRepository.save(cusMission);
        return cusMission.getId();
    }

    @Transactional
    @Override
    public void delete(Long cusId) {
        cusMissionRepository.deleteById(cusId);
    }

    @Transactional
    @Override
    public void accept(Long cusId,Long memberId) {
        CusMission cusMission = cusMissionRepository.findById(cusId).get();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        cusMission.changeCus(member, CusState.WORK);
    }
}
