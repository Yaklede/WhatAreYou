package com.WhatAreYou.WhatAreYou.repository;

import com.WhatAreYou.WhatAreYou.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {

    public List<FileEntity> findByBoardId(Long boardId);
}
