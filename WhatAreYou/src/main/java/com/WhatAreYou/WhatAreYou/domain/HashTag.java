package com.WhatAreYou.WhatAreYou.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id")
    private Board board;

    @Builder
    public HashTag(String tag, Board board) {
        this.tag = tag;
        this.board = board;
    }

    public void tagUpdate(String tag) {
        this.tag = tag;
    }
}
