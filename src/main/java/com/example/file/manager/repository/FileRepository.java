package com.example.file.manager.repository;

import com.example.file.manager.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileData, Long> {

    List<FileData> findByName(String name);

    List<FileData> findByExtension(String extension);

    List<FileData> findByPath(String path);
}
