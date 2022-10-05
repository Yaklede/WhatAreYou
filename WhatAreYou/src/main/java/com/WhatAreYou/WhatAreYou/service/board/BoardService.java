package com.WhatAreYou.WhatAreYou.service.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    public Long create(Board board);
    public void delete(Long boardId);
    public void deleteAllByMemberId(Long memberId);
    public void update(Long boardId,BoardUpdateForm updateForm);
    public Board findByBoardId(Long boardId);
    public List<Board> findAll();

    public Page<Board> findPageAll(Pageable pageable);

    public Page<Board> findSearchPageAll(BoardSearchCondition condition, Pageable pageable);

    public Page<Board> findSearchComplexPageAll(BoardSearchCondition condition, Pageable pageable);
    public List<Board> findAllByMemberId(Long memberId);

    public Board findCommentFetchByBoardId(Long boardId);

}
