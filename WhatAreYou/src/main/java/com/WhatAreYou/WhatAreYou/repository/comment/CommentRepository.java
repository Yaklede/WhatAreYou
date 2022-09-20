package com.WhatAreYou.WhatAreYou.repository.comment;

import com.WhatAreYou.WhatAreYou.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public Comment findByMemberId(Long memberId);
}
