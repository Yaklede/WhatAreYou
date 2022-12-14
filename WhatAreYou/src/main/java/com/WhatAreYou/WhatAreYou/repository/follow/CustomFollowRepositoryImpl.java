package com.WhatAreYou.WhatAreYou.repository.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.WhatAreYou.WhatAreYou.domain.QFollow.*;
import static com.WhatAreYou.WhatAreYou.domain.QMember.*;

public class CustomFollowRepositoryImpl implements CustomFollowRepository {

    private final JPAQueryFactory queryFactory;
    @Autowired
    private EntityManager em;

    public CustomFollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long mFollow(Member fromMember, Member toMember) {
        Follow follow = Follow.builder()
                .toMember(toMember)
                .fromMember(fromMember)
                .build();
        em.persist(follow);
        return follow.getId();
    }

    @Override
    public Long mUnFollow(Member fromMember, Member toMember) {
        return queryFactory
                .delete(follow)
                .where(
                        follow.fromMember.eq(fromMember)
                                .and(
                                        follow.toMember.eq(toMember)
                                )
                )
                .execute();
    }

    @Override
    public Long mFollowState(Member principalMember, Member member) {
        return queryFactory
                    .select(follow.count())
                    .where(
                            follow.fromMember.eq(principalMember)
                                    .and(
                                            follow.toMember.eq(member)
                                    )
                    )
                    .from(follow)
                    .fetchOne();
    }

    @Override
    public Long mFollowingCount(Member member) {
         return  queryFactory
                .select(follow.count())
                .where(
                        follow.toMember.eq(member)
                )
                .from(follow)
                .fetchOne();
    }

    @Override
    public Long mFollowerCount(Member member) {
        return  queryFactory
                .select(follow.count())
                .where(
                        follow.fromMember.eq(member)
                )
                .from(follow)
                .fetchOne();
    }

    @Override
    public List<Follow> mFollowList(Member toMember) {
        return queryFactory
                .selectFrom(follow)
                .where(follow.toMember.eq(toMember))
                .fetch();
    }

    @Override
    public Page<Follow> findPageAll(Pageable pageable) {
        QueryResults<Follow> followQueryResults = queryFactory
                .selectFrom(follow)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(follow.id.desc())
                .fetchResults();

        List<Follow> content = followQueryResults.getResults();
        long total = followQueryResults.getTotal();

        return new PageImpl<>(content, pageable,total);
    }


}
