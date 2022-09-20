package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.QQnA;
import com.WhatAreYou.WhatAreYou.domain.QnA;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.WhatAreYou.WhatAreYou.domain.QQnA.*;

public class CustomQnaRepositoryImpl implements CustomQnaRepository {

    private final JPAQueryFactory queryFactory;

    public CustomQnaRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<QnA> findByNotAnswer(Long qnaId) {
        return queryFactory
                .selectFrom(qnA)
                .where(
                        qnA.id.eq(qnaId).and(
                                qnA.answers.isNull()
                        )
                )
                .fetch();
    }

    @Override
    public int findByNotAnswerCount(Long qnaId) {
        Long result = queryFactory
                .select(qnA.count())
                .where(
                        qnA.id.eq(qnaId).and(
                                qnA.answers.isNull()
                        )
                )
                .from(qnA)
                .fetchOne();
        return (int) (long) result;
    }
}
