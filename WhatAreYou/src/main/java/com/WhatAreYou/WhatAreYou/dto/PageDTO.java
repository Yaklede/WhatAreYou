package com.WhatAreYou.WhatAreYou.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private final List<BoardDTO> content;
    private final int totalPage;
}
