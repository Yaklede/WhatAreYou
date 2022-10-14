package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/{boardId}/{memberId}")
    public String boardCreateLike(@PathVariable("boardId") Long boardId, @PathVariable("memberId") Long memberId) {
        likeService.like(boardId, memberId);
        return "redirect:/board/boardDetail/{boardId}/{memberId}";
    }


    @PostMapping("/unlike/{boardId}/{memberId}")
    public String boardDeleteLike(@PathVariable("boardId") Long boardId,@PathVariable("memberId") Long memberId) {
        likeService.unLike(boardId, memberId);
        return "redirect:/board/boardDetail/{boardId}/{memberId}";
    }
}
