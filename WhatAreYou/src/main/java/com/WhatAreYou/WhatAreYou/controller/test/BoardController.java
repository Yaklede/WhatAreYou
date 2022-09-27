package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.form.CommentForm;
import com.WhatAreYou.WhatAreYou.dto.form.LikeForm;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardForm;
import com.WhatAreYou.WhatAreYou.dto.form.member.LoginForm;
import com.WhatAreYou.WhatAreYou.exception.CommentNotFoundException;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.comment.CommentService;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import com.WhatAreYou.WhatAreYou.service.like.LikeService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test/board")
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final FileService fileService;
    private final LikeService likeService;

    /**
     * 원래 컨트롤러에서는 로그인 된 사용자만 api 호출 할 수 있게 인터셉터를 걸어둘 것이고
     * 전체 보드를 보여주면서 LoginMember의 데이터를 넘겨준다
     */
    @GetMapping("/create")
    public String boardForm(@SessionAttribute("loginMember")Member loginMember, Model model) {
        BoardForm boardForm = new BoardForm();
        boardForm.setLoginId(loginMember.getLoginId());
        model.addAttribute("boardForm", boardForm);
        return "/test/board/create";
    }

    @PostMapping("/create/{memberId}")
    public String createBoard(@PathVariable("memberId")String loginId, @Valid @ModelAttribute("BoardForm") BoardForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "/test/board/create";
        }
        createBoard(loginId, form);
        return "redirect:/test/";
    }

    @GetMapping("/boards")
    public String boardsView(@SessionAttribute("loginMember") Member loginMember, Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        model.addAttribute("member", loginMember);
        return "/test/board/boardList";
    }

    /**
     * model 에 board 하나로만 전달 할 수 있게 수정 해야함
     */

    @GetMapping("/boardDetail/{boardId}/{memberId}")
    public String boardView(@ModelAttribute("commentForm") CommentForm commentForm,@PathVariable("boardId") Long boardId, @PathVariable("memberId") Long memberId,Model model) {
        Board board = boardService.findByBoardId(boardId);
        Member loginMember = memberService.findByOne(memberId);
        Long likeCount = likeService.BoardLikeCount(boardId);
        Long likeState = likeService.likeState(boardId, memberId);
        List<Comment> boardComments = commentService.findByBoardId(boardId);
        model.addAttribute("member", loginMember);
        model.addAttribute("board", board);
        model.addAttribute("likeState", likeState);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("boardComments", boardComments);
        return "test/board/boardView";
    }

    @PostMapping("/like/{boardId}/{memberId}")
    public String boardCreateLike(@PathVariable("boardId") Long boardId,@PathVariable("memberId") Long memberId) {
        likeService.like(boardId, memberId);
        return "redirect:/test/board/boardDetail/{boardId}/{memberId}";
    }
    @PostMapping("/unlike/{boardId}/{memberId}")
    public String boardDeleteLike(@PathVariable("boardId") Long boardId,@PathVariable("memberId") Long memberId) {
        likeService.unLike(boardId, memberId);
        return "redirect:/test/board/boardDetail/{boardId}/{memberId}";
    }

    @PostMapping("/comment/{boardId}/{memberId}")
    public String boardCreateComment(@Valid @ModelAttribute("commentForm") CommentForm commentForm, BindingResult bindingResult, @PathVariable("boardId") Long boardId, @PathVariable("memberId") Long memberId,Model model) {
        if (bindingResult.hasErrors()) {
            List<Comment> boardComments = commentService.findByBoardId(boardId);
            Board board = boardService.findByBoardId(boardId);
            model.addAttribute("board", board);
            model.addAttribute("boardComments", boardComments);
            return "/test/board/boardView";
        }
        commentService.create(memberId, boardId, commentForm.getCommentInput());
        return "redirect:/test/board/boardDetail/{boardId}/{memberId}";
    }

    @PostMapping("/unComment/{commentId}")
    public String boardDeleteComment(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
        return "redirect:/test/board/boards";
    }


    @ResponseBody
    @GetMapping("/image/{fileId}")
    public Resource downloadImg(@PathVariable("fileId") Long fileId, Model model) throws IOException {
        FileEntity fileEntity = fileService.findByOne(fileId);
        if (fileEntity == null) {
            return null;
        }
        UrlResource resource = new UrlResource("file:" + fileEntity.getSavePath());
        return resource;
    }

    private void createBoard(String loginId, BoardForm form) throws IOException {

        Member member = memberService.findByLoginId(loginId);
        Long createdFile = fileService.saveFile(form.getFile());
        FileEntity fileEntity = fileService.findByOne(createdFile);
        Board board = Board.builder()
                .member(member)
                .fileEntity(fileEntity)
                .title(form.getTitle())
                .content(form.getContent())
                .build();
        boardService.create(board);
    }

}
