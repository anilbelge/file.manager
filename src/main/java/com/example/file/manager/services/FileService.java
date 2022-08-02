package com.example.file.manager.services;

import com.example.file.manager.model.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    String getContent(Long id) throws IOException;

    Optional<FileData> findById(Long id);

    List<FileData> findAllDocument();

    FileData addDocument(MultipartFile file) throws IOException;

    FileData updateDocument(Long id, MultipartFile file);

    void deleteDocument(FileData fileData);
}
