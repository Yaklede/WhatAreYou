package com.WhatAreYou.WhatAreYou.service.file;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import com.WhatAreYou.WhatAreYou.exception.FileNotFoundException;
import com.WhatAreYou.WhatAreYou.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FileServiceImpl implements FileService {

    //파일이 저장될 로컬 경로
    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Override
    public Long saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        FileEntity file = createFile(multipartFile);
        multipartFile.transferTo(new File(file.getSavePath()));
        fileRepository.save(file);
        return file.getId();
    }

    @Override
    public Long
    updateFile(FileEntity updateFile, MultipartFile multipartFile) throws IOException {
        FileEntity findFile = fileRepository.findById(updateFile.getId()).orElseThrow(() -> new FileNotFoundException());
        updateFile(multipartFile, findFile);
        multipartFile.transferTo(new File(findFile.getSavePath()));
        return null;
    }

    @Override
    public FileEntity findByOne(Long fileEntityId) {
        return fileRepository.findById(fileEntityId).orElseThrow(() -> new FileNotFoundException());
    }

    @Override
    public List<FileEntity> findByBoardId(Long boardId) {
        return fileRepository.findByBoardId(boardId);
    }

    private void updateFile(MultipartFile multipartFile, FileEntity findFile) {
        FileEntity newFile = createFile(multipartFile);

        findFile.setOrgNm(newFile.getSaveNm());
        findFile.setSaveNm(newFile.getSaveNm());
        findFile.setSavePath(newFile.getSavePath());
    }

    private FileEntity createFile(MultipartFile file) {
        String origName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = origName.substring(origName.lastIndexOf("."));
        String saveName = uuid + extension;
        String savePath = fileDir + saveName;
        FileEntity fileEntity = FileEntity.builder()
                .orgNm(origName)
                .saveNm(saveName)
                .savePath(savePath)
                .build();
        return fileEntity;
    }
}
