package com.WhatAreYou.WhatAreYou.controller.main;

import com.WhatAreYou.WhatAreYou.domain.Board;
import com.WhatAreYou.WhatAreYou.domain.Comment;
import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.Member;
import com.WhatAreYou.WhatAreYou.dto.BoardDTO;
import com.WhatAreYou.WhatAreYou.dto.MemberDTO;
import com.WhatAreYou.WhatAreYou.dto.ProfileDTO;
import com.WhatAreYou.WhatAreYou.service.board.BoardService;
import com.WhatAreYou.WhatAreYou.service.comment.CommentService;
import com.WhatAreYou.WhatAreYou.service.file.FileService;
import com.WhatAreYou.WhatAreYou.service.follow.FollowService;
import com.WhatAreYou.WhatAreYou.service.hashTag.HashTagService;
import com.WhatAreYou.WhatAreYou.service.like.LikeService;
import com.WhatAreYou.WhatAreYou.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final FollowService followService;
    private final HashTagService hashTagService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/profile/{memberId}")
    public String profile(@PathVariable("memberId") Long memberId,Model model) {
        Member member = memberService.findByOne(memberId);
        MemberDTO loginMember = MemberDTO.builder().member(member).build();
        ProfileDTO profile = ProfileDTO.builder()
                .boardCount(boardService.boardCountByMemberId(memberId))
                .boards(getBoardDTOS(memberId))
                .followerCount(followService.followerCount(member))
                .followingCount(followService.followingCount(member))
                .loginMember(member)
                .build();
        model.addAttribute("profile", profile);
        model.addAttribute("loginMember", loginMember);
        return "/main/board/profile";
    }

    @GetMapping("/profile/{followMemberId}/{loginMemberId}")
    public String profileView(@PathVariable("followMemberId") Long followMemberId, @PathVariable("loginMemberId") Long loginMemberId, Model model) {
        if (followMemberId == loginMemberId) {
            return "redirect:/profile/{loginMemberId}";
        }
        Member member = memberService.findByOne(followMemberId);
        ProfileDTO profile = ProfileDTO.builder()
                .boardCount(boardService.boardCountByMemberId(followMemberId))
                .boards(getBoardDTOS(followMemberId))
                .followerCount(followService.followerCount(member))
                .followingCount(followService.followingCount(member))
                .loginMember(member)
                .build();

        Member loginMember = memberService.findByOne(loginMemberId);
        MemberDTO loginMemberDTO = MemberDTO.builder().member(loginMember).build();
        Long followState = followService.followState(followMemberId, loginMemberId);
        model.addAttribute("profile", profile);
        model.addAttribute("loginMember", loginMemberDTO);
        model.addAttribute("followState", followState);
        return "/main/board/profile";
    }



    private List<BoardDTO> getBoardDTOS(Long memberId) {
        List<Board> content = boardService.findAllByMemberId(memberId);
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
