package com.WhatAreYou.WhatAreYou.repository.board;


import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.QBoard;
import com.WhatAreYou.WhatAreYou.domain.QComment;
import com.WhatAreYou.WhatAreYou.domain.QLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.WhatAreYou.WhatAreYou.domain.QBoard.*;
import static com.WhatAreYou.WhatAreYou.domain.QComment.*;
import static com.WhatAreYou.WhatAreYou.domain.QLike.*;

public class BoardViewQueryImpl implements BoardViewQuery {

    private final JPAQueryFactory queryFactory;

    public BoardViewQueryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Board findCommentFetchByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(board)
                .join(board.comments, comment1).fetchJoin()
                .where(comment1.board.id.eq(boardId))
                .fetchOne();
    }
}
