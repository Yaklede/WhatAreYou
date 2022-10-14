package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.dto.form.comment.CommentForm;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    @PostMapping("/comment/{boardId}/{memberId}")
    public String boardCreateComment(@Valid @ModelAttribute("commentForm") CommentForm commentForm, BindingResult bindingResult, @PathVariable("boardId") Long boardId, @PathVariable("memberId") Long memberId, Model model) {
        if (bindingResult.hasErrors()) {
            List<Comment> boardComments = commentService.findByBoardId(boardId);
            Board board = boardService.findByBoardId(boardId);
            model.addAttribute("board", board);
            model.addAttribute("boardComments", boardComments);
            return "/board/boardView";
        }
        commentService.create(memberId, boardId, commentForm.getCommentInput());
        return "redirect:/board/boardDetail/{boardId}/{memberId}";
    }

    @PostMapping("/unComment/{commentId}")
    public String boardDeleteComment(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
        return "redirect:/";
    }
}
