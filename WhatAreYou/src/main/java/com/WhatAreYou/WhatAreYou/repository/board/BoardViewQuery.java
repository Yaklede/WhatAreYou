package com.WhatAreYou.WhatAreYou.repository.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardViewQuery {
    //comment , like , board;
    public Board findCommentFetchByBoardId(Long boardId);

    public Page<Board> findPageAll(Pageable pageable);

    public Page<Board> findSearchPageAll(BoardSearchCondition condition, Pageable pageable);

    public Page<Board> findSearchComplexPageAll(BoardSearchCondition condition, Pageable pageable);

    public Page<Board> findRankingAll(Pageable pageable);
}
