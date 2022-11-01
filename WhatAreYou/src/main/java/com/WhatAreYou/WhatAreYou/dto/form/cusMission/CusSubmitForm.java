package com.WhatAreYou.WhatAreYou.dto.form.cusMission;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CusSubmitForm {
    private MultipartFile file;
}
