package com.WhatAreYou.WhatAreYou.repository.member;

import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.domain.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import java.util.List;

import static com.WhatAreYou.WhatAreYou.domain.QMember.member;

public class CustomMemberRepositoryImpl implements  CustomMemberRepository  {

    @Autowired
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public CustomMemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Member> findPageAll(Pageable pageable) {
        QueryResults<Member> memberQueryResults = queryFactory
                .selectFrom(member)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(member.followerCount.desc())
                .fetchResults();

        List<Member> content = memberQueryResults.getResults();
        long total = memberQueryResults.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
