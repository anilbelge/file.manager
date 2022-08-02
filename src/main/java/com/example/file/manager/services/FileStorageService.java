package com.example.file.manager.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeDocument(MultipartFile file);

    void deleteFile(String path);
}
