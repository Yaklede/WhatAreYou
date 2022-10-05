package com.WhatAreYou.WhatAreYou.repository.hashtag;

import com.WhatAreYou.WhatAreYou.domain.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {

    public HashTag findByBoardId(Long BoardId);
}
