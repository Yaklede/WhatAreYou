package com.WhatAreYou.WhatAreYou.service.hashTag;

import com.WhatAreYou.WhatAreYou.domain.HashTag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashTagService {
    public Long create(Long boardId, String hashTag);
    public HashTag findByBoardId(Long boardId);
    public void delete(Long boardId);
}
