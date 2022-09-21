package com.WhatAreYou.WhatAreYou.repository.like;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Member;

public interface CustomLikeRepository {
    public Long mLike(Board fromBoard, Member toMember);

    public Long mUnLike(Board fromBoard, Member toMember);

    public Long mLikeState(Board fromBoard, Member principalMember);

    //내가 좋아요 누른 게시글
    public Long mLikeCount(Member member);

    //게시판 좋아요 개수 조회
    public Long mBoardLikeCount(Board fromBoard);
}
