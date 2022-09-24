package com.WhatAreYou.WhatAreYou.service.board;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardUpdateForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    public Long create(Board board);
    public void delete(Long boardId);
    public void update(Long boardId,BoardUpdateForm updateForm);
    public Board findByBoardId(Long boardId);
    public List<Board> findAll();
    public List<Board> findAllByMemberId(Long memberId);
}
