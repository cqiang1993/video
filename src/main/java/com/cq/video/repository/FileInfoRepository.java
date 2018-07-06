package com.cq.video.repository;

import com.cq.video.entity.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface FileInfoRepository extends MongoRepository<FileInfo,String> {

    <S extends FileInfo> S save(S s);

    Page<FileInfo> findAll(Pageable pageable);

    void deleteById(String id);

    Optional<FileInfo> findByIdAndServerName(String id, String serverName);
}
