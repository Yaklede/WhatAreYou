package com.WhatAreYou.WhatAreYou.domain;

import com.WhatAreYou.WhatAreYou.base.BaseEntity;
import com.WhatAreYou.WhatAreYou.dto.form.member.MemberUpdateForm;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String nickName;
    private String email;
    private int age;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private FileEntity fileEntity;

    @Transient
    private Long followState;

    private Long followerCount;

    @Builder
    public Member(@NotEmpty String loginId, @NotEmpty String password, @NotEmpty String nickName, @NotEmpty String email, @NotEmpty int age, FileEntity fileEntity) {
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.age = age;
        this.fileEntity = fileEntity;
    }

    public void updateMember(MemberUpdateForm updateForm) {
        this.password = updateForm.getPassword();
        this.nickName = updateForm.getNickName();
        this.age = updateForm.getAge();
        this.email = updateForm.getEmail();
        this.fileEntity = updateForm.getFileEntity();
    }

    public void addFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

}
