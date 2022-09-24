package com.WhatAreYou.WhatAreYou.service.file;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    public Long saveFile(MultipartFile file) throws IOException;
    public Long updateFile(FileEntity updateFile, MultipartFile file) throws IOException;

    public FileEntity findByOne(Long fileEntityId);

    public List<FileEntity> findByBoardId(Long boardId);

}