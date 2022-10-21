package com.WhatAreYou.WhatAreYou.dto;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.HashTag;
import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardDTO {
    private Long boardId;
    private Long boardCreateMemberId;
    private Long fileId;
    private Long loginMemberId;
    private String loginId;
    private String boardCreator;
    private String title;
    private String content;
    private Long likeCount;
    private Long likeState;
    private Long commentCount;
    private Long followingCount;
    private Long followerCount;
    private int totalPage;
    List<CommentDTO> comments;
    String[] hashTags;

    @Builder
    public BoardDTO(Board board, Member loginMember, Long likeState, Long likeCount, List<Comment> comments,String[] hashTags,Long commentCount,int totalPage) {
        this.boardId = board.getId();
        this.fileId = board.getFileEntity().getId();
        this.loginMemberId = loginMember.getId();
        this.loginId = loginMember.getLoginId();
        this.boardCreator = board.getMember().getLoginId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCount = likeCount;
        this.likeState = likeState;
        this.comments = comments.stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
        this.hashTags = hashTags;
        this.commentCount = commentCount;
        this.boardCreateMemberId = board.getMember().getId();
        this.totalPage = totalPage;
    }

    public BoardDTO(Board board, Member loginMember) {
        this.boardId = board.getId();
        this.fileId = board.getFileEntity().getId();
        this.loginMemberId = loginMember.getId();
        this.boardCreator = board.getMember().getLoginId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }

    public BoardDTO(Board board, Member loginMember,Long likeCount,Long commentCount,String[] hashTags) {
        this.boardId = board.getId();
        this.fileId = board.getFileEntity().getId();
        this.loginMemberId = loginMember.getId();
        this.boardCreator = board.getMember().getLoginId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.hashTags = hashTags;
    }


}
