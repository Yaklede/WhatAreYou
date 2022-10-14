package com.WhatAreYou.WhatAreYou.controller.test;

import com.WhatAreYou.WhatAreYou.domain.*;
import com.WhatAreYou.WhatAreYou.dto.BoardDTO;
import com.WhatAreYou.WhatAreYou.dto.form.comment.CommentForm;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardForm;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.comment.CommentService;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import com.WhatAreYou.WhatAreYou.service.hashTag.HashTagService;
import com.WhatAreYou.WhatAreYou.service.like.LikeService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final HashTagService hashTagService;

    /**
     * 원래 컨트롤러에서는 로그인 된 사용자만 api 호출 할 수 있게 인터셉터를 걸어둘 것이고
     * 전체 보드를 보여주면서 LoginMember의 데이터를 넘겨준다
     */


    @GetMapping("/create")
    public String boardForm(@SessionAttribute("loginMember") Member loginMember, Model model) {
        BoardForm boardForm = new BoardForm();
        boardForm.setCreatedId(loginMember.getLoginId());
        model.addAttribute("boardForm", boardForm);
        return "/test/board/create";
    }

    @PostMapping("/create/{memberId}")
    public String createBoard(@Valid @ModelAttribute("boardForm") BoardForm form, BindingResult bindingResult, @PathVariable("memberId")String loginId, Model model) throws IOException {
        log.info("bindingResult = {}", bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            return "/test/board/create";
        }
        String x = BoardValidator(form, bindingResult);
        if (x != null) return x;
        createBoard(loginId, form);

        return "redirect:/test/";
    }

    private String BoardValidator(BoardForm form, BindingResult bindingResult) {
        if (!form.getHashTag().substring(0, 1).equals("#") || form.getHashTag().contains(",") || form.getFile().isEmpty()) {
            if (form.getHashTag().contains(",")) {
                bindingResult.reject("태그에는 , 불가","ex)#태그#태그2 형식으로 작성해야 하고 , 는 사용이 불가능합니다.");
                return "/test/board/create";
            }
            if (form.getFile().isEmpty()) {
                bindingResult.reject("이미지 없음","게시글에 이미지는 필수입니다..");
                return "/test/board/create";
            }
            bindingResult.reject("태그에는 # 필수","해시태그에는 #이 필수입니다.");
            return "/test/board/create";

        }
        return null;
    }

    @PostMapping("/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        commentService.deleteAll(boardId);
        likeService.BoardDeleteLike(boardId);
        hashTagService.delete(boardId);
        boardService.delete(boardId);
        return "redirect:/test/board";
    }

    @GetMapping("/boards")
    public String boardsView(@SessionAttribute("loginMember") Member loginMember,@ModelAttribute("condition") BoardSearchCondition condition, @PageableDefault(size = 1) Pageable pageable, Model model) {
        Page<Board> pageBoards = boardService.findSearchPageAll(condition,pageable);
        List<Board> boards = pageBoards.getContent();
        int totalPages = pageBoards.getTotalPages();
        List<BoardDTO> boardDTOList = boards.stream().map(board -> new BoardDTO(board, loginMember)).collect(Collectors.toList());
        model.addAttribute("boards", boardDTOList);
        model.addAttribute("totalPages", totalPages);
        return "/test/board/boardList";
    }

    /**
     * model 에 board 하나로만 전달 할 수 있게 수정 해야함
     */

    @GetMapping("/boardDetail/{boardId}/{memberId}")
    public String boardView(@PathVariable("boardId") Long boardId, @PathVariable("memberId") Long memberId,Model model) {
        BoardDTO boardDTO = getBoardDTO(boardId, memberId);
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("boardDTO", boardDTO);
        return "test/board/boardView";
    }

    private BoardDTO getBoardDTO(Long boardId, Long memberId) {
        Board board = boardService.findByBoardId(boardId);
        Member loginMember = memberService.findByOne(memberId);
        Long likeCount = likeService.BoardLikeCount(boardId);
        Long likeState = likeService.likeState(boardId, memberId);
        List<Comment> boardComments = commentService.findByBoardId(boardId);
        HashTag tag = hashTagService.findByBoardId(boardId);

        String[] split = tag.getTag().split("#");
        String[] hashTags = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                int index = i;
                hashTags[index - 1] = "#" + split[i];
            }
        }
        for (String hashTag : hashTags) {
            log.info("tag = {}", hashTag);
        }

        BoardDTO boardDTO = BoardDTO.builder()
                .board(board)
                .comments(boardComments)
                .hashTags(hashTags)
                .loginMember(loginMember)
                .likeCount(likeCount)
                .likeState(likeState)
                .build();
        return boardDTO;
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
        hashTagService.create(board.getId(), form.getHashTag());
    }


}
