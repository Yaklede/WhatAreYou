package com.WhatAreYou.WhatAreYou.domain;

import com.WhatAreYou.WhatAreYou.base.BaseEntity;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;

    @OneToOne(mappedBy = "board")
    private HashTag hashTags;

    private Long likeCount;

    @Builder
    public Board(String title, String content, Member member, FileEntity fileEntity) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.fileEntity = fileEntity;
    }

    public void updateBoard(BoardUpdateForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.fileEntity = form.getFileEntity();
    }

    public void addLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
