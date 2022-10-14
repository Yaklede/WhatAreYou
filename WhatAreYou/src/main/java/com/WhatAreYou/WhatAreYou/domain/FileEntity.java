package com.WhatAreYou.WhatAreYou.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    //파일 원래 이름
    private String orgNm;
    //파일 저장 이름 -> uuid
    private String saveNm;
    //파일 저장 경로
    private String savePath;

    @OneToOne(mappedBy = "fileEntity")
    private Board board;

    @OneToOne(mappedBy = "fileEntity")
    private Member member;

    @Builder
    public FileEntity(String orgNm, String saveNm, String savePath) {
        this.orgNm = orgNm;
        this.saveNm = saveNm;
        this.savePath = savePath;
    }
}
