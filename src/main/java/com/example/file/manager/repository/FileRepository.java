package com.example.file.manager.repository;

import com.example.file.manager.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository
        extends JpaRepository<FileData, Long> {
}
