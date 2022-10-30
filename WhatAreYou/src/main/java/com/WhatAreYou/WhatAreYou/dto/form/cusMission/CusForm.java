package com.WhatAreYou.WhatAreYou.dto.form.cusMission;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CusForm {
    private Long requesterId;
    private String requesterName;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @Min(value = 1000)
    private int price;

}
