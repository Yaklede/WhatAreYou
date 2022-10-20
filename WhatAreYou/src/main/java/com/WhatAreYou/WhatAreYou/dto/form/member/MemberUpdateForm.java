package com.WhatAreYou.WhatAreYou.dto.form.member;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MemberUpdateForm {
    private Long id;
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotNull
    private int age;
    private MultipartFile file;
    private FileEntity fileEntity;

    @Builder
    public MemberUpdateForm(@NotEmpty String nickName,@NotEmpty String password, @NotEmpty String email, @NotNull int age,MultipartFile file) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.age = age;
        this.file = file;
    }

    public MemberUpdateForm(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.password = member.getPassword();
        this.age = member.getAge();
        this.email = member.getEmail();
    }
}
