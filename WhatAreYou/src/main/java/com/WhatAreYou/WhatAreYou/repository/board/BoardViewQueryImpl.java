package com.WhatAreYou.WhatAreYou.repository.board;


import com.WhatAreYou.WhatAreYou.domain.*;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.repository.like.LikeRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.WhatAreYou.WhatAreYou.domain.QBoard.*;
import static com.WhatAreYou.WhatAreYou.domain.QComment.*;
import static com.WhatAreYou.WhatAreYou.domain.QHashTag.*;
import static com.WhatAreYou.WhatAreYou.domain.QLike.*;

@Slf4j

public class BoardViewQueryImpl implements BoardViewQuery {

    private final JPAQueryFactory queryFactory;


    @Autowired
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

    @Override
    public Page<Board> findPageAll(Pageable pageable) {
        List<Board> content = queryFactory
                .selectFrom(board)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(board.likeCount.desc())
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .fetchOne();

        return new PageImpl<>(content,pageable,count);
    }
    @Override
    public Page<Board> findRankingAll(Pageable pageable) {
        List<Board> content = queryFactory
                .selectFrom(board)
                .limit(3)
                .offset(0)
                .orderBy(board.likeCount.desc())
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .fetchOne();

        return new PageImpl<>(content,pageable,count);
    }

    @Override
    public Page<Board> findSearchPageAll(BoardSearchCondition condition, Pageable pageable) {
        QueryResults<Board> results = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.hashTags, hashTag)
                .where(
                        titleTagEq(condition.getQuery())

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Board> findSearchComplexPageAll(BoardSearchCondition condition, Pageable pageable) {
        List<Board> content = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.hashTags, hashTag)
                .where(
                        titleTagEq(condition.getQuery())

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Board> count = queryFactory.selectFrom(board)
                .where(
                        titleTagEq(condition.getQuery()));
        return PageableExecutionUtils.getPage(content, pageable,count::fetchCount);
    }

    private BooleanExpression tagEq(String tag) {
        return StringUtils.hasText(tag) ? hashTag.tag.eq(tag) : null;
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? board.title.eq(title) : null;
    }

    private BooleanExpression titleTagEq(String query) {
        if (StringUtils.hasText(query)) {
            try {
                String decodeQuery = URLDecoder.decode(query, "UTF-8").replaceAll(" ", "");
                log.info("decode Query = {}",decodeQuery);
                String[] split = decodeQuery.split("#");
                if (decodeQuery.substring(0, 1).equals("#")) {
                    String ExTag = "#" + split[1];
                    log.info("tag = {}", ExTag);
                    return hashTag.tag.contains(decodeQuery).or(hashTag.tag.contains(ExTag));
                } else {
                    return board.title.contains(split[0]);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
