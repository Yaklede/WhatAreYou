package com.WhatAreYou.WhatAreYou.service.cusmission;

import com.WhatAreYou.WhatAreYou.domain.CusMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CusMissionService {
    public List<CusMission> findAll();
    public CusMission findById(Long cusId);

    public Long create(CusMission cusMission);

    public void delete(Long cusId);

    public void accept(Long cusId,Long memberId);
}
