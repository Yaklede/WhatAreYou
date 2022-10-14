package com.WhatAreYou.WhatAreYou.repository.comment;

import com.WhatAreYou.WhatAreYou.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public List<Comment> findByMemberId(Long memberId);
    public List<Comment> findByBoardId(Long boardId);

    @Query("select COUNT(c) from Comment c where c.board.id = :boardId")
    public Long countByBoardId(@Param("boardId") Long boardId);

}
