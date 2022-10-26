package com.WhatAreYou.WhatAreYou.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private  List<BoardDTO> content;
    private int totalPage;

    public PageDTO(List<BoardDTO> boardDTOS, int totalPages) {
    }
}
