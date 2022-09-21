package com.WhatAreYou.WhatAreYou.repository.like;

import com.WhatAreYou.WhatAreYou.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long>, CustomLikeRepository {

    @Query("select l from Like l join fetch l.fromBoard b where b.id = :boardId")
    public List<Like> findAllByBoardId(@Param("boardId") Long boardId);
}
