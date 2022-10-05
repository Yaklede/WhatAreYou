package com.WhatAreYou.WhatAreYou.service.comment;

import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.dto.form.comment.CommentUpdateForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    public Long create(Long memberId, Long boardId, String comment);

    public void update(Long commentId, CommentUpdateForm updateComment);

    public void delete(Long commentId);

    public void deleteAll(Long boardId);

    public List<Comment> findAll();

    public List<Comment> findByMemberId(Long memberId);

    public List<Comment> findByBoardId(Long boardId);
}
