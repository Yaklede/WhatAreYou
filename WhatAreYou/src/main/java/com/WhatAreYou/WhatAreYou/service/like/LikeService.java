package com.WhatAreYou.WhatAreYou.service.like;

import com.WhatAreYou.WhatAreYou.domain.Like;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
    public Long like(Long fromBoardId, Long toMemberId);

    public Long unLike(Long fromBoardId, Long toMemberId);

    public Long likeState(Long pageBoardId ,Long loginMemberId);

    public List<Like> likeList(Long fromBoardId);

    public Long MemberLikeCount(Long memberId);

    public Long BoardLikeCount(Long boardId);

    public void BoardDeleteLike(Long boardId);
}
