package com.WhatAreYou.WhatAreYou.domain;

import com.WhatAreYou.WhatAreYou.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="likes")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_board_id")
    private Board fromBoard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_like_member_id")
    private Member toLikeMember;

    @Builder
    public Like(Board fromBoard, Member toLikeMember) {
        this.fromBoard = fromBoard;
        this.toLikeMember = toLikeMember;
    }
}
