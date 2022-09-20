package com.WhatAreYou.WhatAreYou.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String email;

    @NotNull
    private int age;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Member(@NotEmpty String loginId, @NotEmpty String password, @NotEmpty String nickName, @NotEmpty String email, @NotEmpty int age) {
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.age = age;
    }

}
