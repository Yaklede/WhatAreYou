package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QFollow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;

import java.util.List;

import static com.WhatAreYou.WhatAreYou.domain.QFollow.*;

public class CustomFollowRepositoryImpl implements CustomFollowRepository {

    private final JPAQueryFactory queryFactory;

    public CustomFollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int mUnFollow(Member fromMember, Member toMember) {
        long result = queryFactory
                .delete(follow)
                .where(
                        follow.fromMember.eq(fromMember)
                                .and(
                                        follow.toMember.eq(toMember)
                                )
                )
                .execute();
        return (int) result;
    }

    @Override
    public int mFollowState(Member principalMember, Member member) {
        Long fetch = queryFactory
                .select(follow.count())
                .where(
                        follow.fromMember.eq(principalMember)
                                .and(
                                        follow.toMember.eq(member)
                                )
                )
                .from(follow)
                .fetchOne();
        int result = (int) (long) fetch;
        return result;
    }

    @Override
    public int mFollowCount(Member member) {
        Long fetchOne = queryFactory
                .select(follow.count())
                .where(
                        follow.fromMember.eq(member)
                )
                .from(follow)
                .fetchOne();

        return (int) (long) fetchOne;
    }
}
