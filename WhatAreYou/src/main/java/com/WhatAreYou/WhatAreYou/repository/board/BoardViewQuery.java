package com.WhatAreYou.WhatAreYou.repository.board;

import com.WhatAreYou.WhatAreYou.domain.Board;

import java.util.List;

public interface BoardViewQuery {
    //comment , like , board;
    public Board findCommentFetchByBoardId(Long boardId);
}
