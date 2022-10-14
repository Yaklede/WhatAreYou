package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.domain.*;
import com.WhatAreYou.WhatAreYou.dto.BoardDTO;
import com.WhatAreYou.WhatAreYou.dto.FollowDTO;
import com.WhatAreYou.WhatAreYou.dto.MemberDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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
    public String indexPage(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Pageable pageable, Model model) {

        if (loginMember == null) {
            List<BoardDTO> boards = getBoardDTOS(pageable);
            List<FollowDTO> follows = getFollowDTOS(pageable);
            List<BoardDTO> boardRanking = getBoardRankingDTOS(pageable);
            model.addAttribute("boards", boards);
            model.addAttribute("rankings", boardRanking);
            model.addAttribute("follows", follows);
            return "/main/notLogin";
        }
        List<BoardDTO> boards = getBoardDTOS(pageable);
        List<FollowDTO> follows = getFollowDTOS(pageable);
        List<BoardDTO> boardRanking = getBoardRankingDTOS(pageable);
        MemberDTO member = MemberDTO.builder().member(loginMember).build();
        model.addAttribute("boards", boards);
        model.addAttribute("follows", follows);
        model.addAttribute("rankings", boardRanking);
        model.addAttribute("member", member);
        return "/main/index";
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

    private List<FollowDTO> getFollowDTOS(@PageableDefault(size = 3,sort = "followerCount",direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Member> PageMember = memberService.findPageAll(pageable);
        List<Member> content = PageMember.getContent();
        return content.stream().map(member ->
                new FollowDTO(
                    member.getFileEntity().getId(), member.getNickName(), member.getCreate_at(),
                    followService.followerCount(member),
                    boardService.boardCountByMemberId(member.getId()))).collect(Collectors.toList());
    }

    private List<BoardDTO> getBoardRankingDTOS(@PageableDefault(size = 3) Pageable pageable) {
        Page<Board> PageBoard = boardService.findPageAll(pageable);
        List<Board> content = PageBoard.getContent();
        return content.stream().map(board ->
                new BoardDTO(board, board.getMember(),
                        likeService.BoardLikeCount(board.getId()),
                        commentService.countByBoardId(board.getId()),
                        getHashTags(board))).collect(Collectors.toList());
    }
    private List<BoardDTO> getBoardDTOS(@PageableDefault(size = 20) Pageable pageable) {
        Page<Board> PageBoard = boardService.findPageAll(pageable);
        List<Board> content = PageBoard.getContent();
        return content.stream().map(board ->
                new BoardDTO(board, board.getMember(),
                        likeService.BoardLikeCount(board.getId()),
                        commentService.countByBoardId(board.getId()),
                        getHashTags(board))).collect(Collectors.toList());
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
