package com.WhatAreYou.WhatAreYou.domain;

import com.WhatAreYou.WhatAreYou.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.boot.model.source.spi.FetchCharacteristics;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CusMission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member requester;

    @OneToOne(fetch = FetchType.LAZY)
    private Member worker;

    private String title;

    private String content;

    private int price;

    @OneToOne(fetch = FetchType.LAZY)
    private ChatRoom room;

    @Enumerated(EnumType.STRING)
    private CusState cusState;

    @Builder
    public CusMission(Member requester, Member worker, String title, String content, int price, CusState cusState,ChatRoom chatRoom) {
        this.requester = requester;
        this.worker = worker;
        this.title = title;
        this.content = content;
        this.price = price;
        this.cusState = cusState;
        this.room = chatRoom;
    }

    public void changeCus(Member worker, CusState cusState) {
        this.worker = worker;
        this.cusState = cusState;
    }
}
