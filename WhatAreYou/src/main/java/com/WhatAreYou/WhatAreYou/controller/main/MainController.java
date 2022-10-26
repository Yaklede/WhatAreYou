package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.domain.*;
import com.WhatAreYou.WhatAreYou.dto.BoardDTO;
import com.WhatAreYou.WhatAreYou.dto.FollowDTO;
import com.WhatAreYou.WhatAreYou.dto.MemberDTO;
import com.WhatAreYou.WhatAreYou.dto.PageDTO;
import com.WhatAreYou.WhatAreYou.dto.form.board.BoardSearchCondition;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.comment.CommentService;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import com.WhatAreYou.WhatAreYou.service.follow.FollowService;
import com.WhatAreYou.WhatAreYou.service.hashTag.HashTagService;
import com.WhatAreYou.WhatAreYou.service.like.LikeService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final FollowService followService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final HashTagService hashTagService;
    private final FileService fileService;

    @GetMapping("/")
    public String indexPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember,@PageableDefault(size = 1) Pageable pageable, Model model) {

        if (loginMember == null) {
            BoardSearchCondition condition = new BoardSearchCondition();
            PageDTO pageDTOS = getPageDTOS(condition,pageable);
            List<FollowDTO> follows = getFollowDTOS(pageable);
            List<BoardDTO> boardRanking = getBoardRankingDTOS(pageable);
            model.addAttribute("boards", pageDTOS.getContent());
            model.addAttribute("totalPage", pageDTOS.getTotalPage());
            model.addAttribute("rankings", boardRanking);
            model.addAttribute("follows", follows);
            model.addAttribute("condition",condition);
            return "/main/notLogin";
        }
        BoardSearchCondition condition = new BoardSearchCondition();
        List<FollowDTO> follows = getFollowDTOS(pageable);
        List<BoardDTO> boardRanking = getBoardRankingDTOS(pageable);
        MemberDTO member = MemberDTO.builder().member(loginMember).build();
        PageDTO pageDTOS = getPageDTOS(condition, pageable);

        model.addAttribute("boards", pageDTOS.getContent());
        model.addAttribute("totalPage", pageDTOS.getTotalPage());
        model.addAttribute("follows", follows);
        model.addAttribute("rankings", boardRanking);
        model.addAttribute("member", member);
        model.addAttribute("condition",condition);
        return "/main/index";
    }

    @GetMapping("/search")
    public String search(@SessionAttribute(name = "loginMember", required = false) Member loginMember, @ModelAttribute("condition") BoardSearchCondition condition, @PageableDefault(size = 1, sort = "id") Pageable pageable, Model model) {
        if (loginMember == null) {
            PageDTO pageDTOS = getPageDTOS(condition, pageable);
            model.addAttribute("boards", pageDTOS.getContent());
            model.addAttribute("totalPage", pageDTOS.getTotalPage());
            model.addAttribute("condition", condition);
            return "/main/board/searchNotLoginPage";
        }

        MemberDTO member = MemberDTO.builder().member(loginMember).build();
        PageDTO pageDTOS = getPageDTOS(condition, pageable);

        model.addAttribute("boards", pageDTOS.getContent());
        model.addAttribute("totalPage", pageDTOS.getTotalPage());
        model.addAttribute("condition", condition);
        model.addAttribute("member", member);
        return "/main/board/searchLoginPage";
    }
    @GetMapping("/searchPage")
    public String searchPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember,@RequestParam("query")String query,@PageableDefault(size = 1,sort = "id") Pageable pageable,Model model) {
        if (loginMember == null) {
            BoardSearchCondition condition = new BoardSearchCondition(query);
            PageDTO pageDTOS = getPageDTOS(condition, pageable);
            model.addAttribute("boards", pageDTOS.getContent());
            model.addAttribute("totalPage", pageDTOS.getTotalPage());
            model.addAttribute("condition", condition);
            return "/main/board/searchNotLoginPage";
        }
        BoardSearchCondition condition = new BoardSearchCondition(query);
        MemberDTO member = MemberDTO.builder().member(loginMember).build();
        PageDTO pageDTOS = getPageDTOS(condition, pageable);

        model.addAttribute("boards", pageDTOS.getContent());
        model.addAttribute("totalPage", pageDTOS.getTotalPage());
        model.addAttribute("condition", condition);
        model.addAttribute("member", member);
        return "/main/board/searchLoginPage";
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

    private List<FollowDTO> getFollowDTOS(Pageable pageable) {
        Page<Member> PageMember = memberService.findRankingAll(pageable);
        List<Member> content = PageMember.getContent();
        return content.stream().map(member ->
                new FollowDTO(
                    member.getFileEntity().getId(),member.getId(),member.getNickName(), member.getCreate_at(),
                    followService.followerCount(member),
                    boardService.boardCountByMemberId(member.getId()))).collect(Collectors.toList());
    }

    private List<BoardDTO> getBoardRankingDTOS(Pageable pageable) {
        Page<Board> PageBoard = boardService.findRankingAll(pageable);
        log.info("page = {}", pageable.getPageSize());
        List<Board> content = PageBoard.getContent();
        return content.stream().map(board ->
                new BoardDTO(board, board.getMember(),
                        likeService.BoardLikeCount(board.getId()),
                        commentService.countByBoardId(board.getId()),
                        getHashTags(board)))
                .collect(Collectors.toList());
    }
    private PageDTO getPageDTOS(BoardSearchCondition condition,Pageable pageable) {
        Page<Board> PageBoard = boardService.findSearchPageAll(condition,pageable);
        List<Board> content = PageBoard.getContent();
        List<BoardDTO> boardDTOS = content.stream().map(board ->
                new BoardDTO(board, board.getMember(),
                        likeService.BoardLikeCount(board.getId()),
                        commentService.countByBoardId(board.getId()),
                        getHashTags(board)))
                .collect(Collectors.toList());
        return new PageDTO(boardDTOS, PageBoard.getTotalPages());
    }

    private String[] getHashTags(Board board) {
        String[] split = hashTagService.findByBoardId(board.getId()).getTag().split("#");
        String[] hashTags = new String[split.length-1];
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                int index = i;
                hashTags[index - 1] = "#" + split[i];
            }
        }
        for (String hashTag : hashTags) {
            log.info("hashTag = {}", hashTag);
        }
        return hashTags;
    }
}
