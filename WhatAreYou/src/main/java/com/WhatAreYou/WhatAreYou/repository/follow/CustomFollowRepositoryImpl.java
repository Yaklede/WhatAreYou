package com.WhatAreYou.WhatAreYou.repository.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static com.WhatAreYou.WhatAreYou.domain.QFollow.*;

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
    public Long mFollowCount(Member member) {
         return  queryFactory
                .select(follow.count())
                .where(
                        follow.fromMember.eq(member)
                )
                .from(follow)
                .fetchOne();
    }
}
