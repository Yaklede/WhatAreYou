package com.WhatAreYou.WhatAreYou.repository.like;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Like;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static com.WhatAreYou.WhatAreYou.domain.QLike.*;

public class CustomLikeRepositoryImpl implements CustomLikeRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    public CustomLikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long mLike(Board fromBoard, Member toMember) {
        Like like = Like.builder()
                .fromBoard(fromBoard)
                .toLikeMember(toMember)
                .build();
        em.persist(like);
        return like.getId();
    }

    @Override
    public Long mUnLike(Board fromBoard, Member toMember) {
        return queryFactory
                .delete(like)
                .where(
                        like.fromBoard.eq(fromBoard)
                                .and(
                                        like.toLikeMember.eq(toMember)
                                )
                ).execute();
    }

    @Override
    public Long mLikeState(Board fromBoard, Member principalMember) {
        return queryFactory
                .select(like.count())
                .where(
                        like.fromBoard.eq(fromBoard)
                                .and(
                                        like.toLikeMember.eq(principalMember)
                                )
                )
                .from(like)
                .fetchOne();
    }

    @Override
    public Long mLikeCount(Member member) {
        return queryFactory
                .select(like.count())
                .where(
                        like.toLikeMember.eq(member)
                )
                .from(like)
                .fetchOne();
    }

    @Override
    public Long mBoardLikeCount(Board fromBoard) {
        return queryFactory
                .select(like.count())
                .where(
                        like.fromBoard.eq(fromBoard)
                )
                .from(like)
                .fetchOne();

    }
}
