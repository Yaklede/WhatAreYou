package com.WhatAreYou.WhatAreYou.service.hashTag;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.HashTag;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardForm;
import com.WhatAreYou.WhatAreYou.exception.BoardNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.board.BoardRepository;
import com.WhatAreYou.WhatAreYou.repository.hashtag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HashTagServiceImpl implements HashTagService {

    private final BoardRepository boardRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    @Override
    public Long create(Long boardId, String hashTag) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException());
        //replaceAll을 통해 공백제거
        HashTag tag = HashTag.builder()
                .tag(hashTag.replaceAll(" ", ""))
                .board(board)
                .build();
        hashTagRepository.save(tag);
        return tag.getId();
    }

    @Override
    public HashTag findByBoardId(Long boardId) {
        return hashTagRepository.findByBoardId(boardId);
    }

    @Transactional
    @Override
    public void delete(Long boardId) {
        HashTag tag = hashTagRepository.findByBoardId(boardId);
        hashTagRepository.delete(tag);
    }
}
