package com.WhatAreYou.WhatAreYou.dto;

import com.WhatAreYou.WhatAreYou.domain.HashTag;
import lombok.Data;

@Data
public class HashTagDTO {
    private String hashtag;

    public HashTagDTO(HashTag hashTag) {
        this.hashtag = hashTag.getTag();
    }
}
