package com.WhatAreYou.WhatAreYou.repository.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>, BoardViewQuery {
    //ToOne Fetch
    @Query("select b from Board b join fetch b.member m where m.id = :memberId")
    public List<Board> findAllByMemberId(@Param("memberId") Long memberId);


}
