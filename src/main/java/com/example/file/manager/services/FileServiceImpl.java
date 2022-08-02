package com.example.file.manager.services;

import com.example.file.manager.exceptions.ApiException;
import com.example.file.manager.model.FileData;
import com.example.file.manager.repository.FileRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, FileStorageService fileStorageService) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public String getContent(Long id) throws IOException {
        Optional<FileData> fileData = this.findById(id);
        if (fileData.isPresent()) {
            File file = new File(fileData.get().getPath());
            if (file.exists()) {
                return Arrays.toString(FileUtils.readFileToByteArray(file));
            } else {
                throw new ApiException("No content found for id :" + id);
            }
        } else {
            throw new EntityNotFoundException("No records Found for id :" + id);
        }
    }

    @Override
    public Optional<FileData> findById(Long id) {
        return Optional.ofNullable(fileRepository.findById(id)
                .orElse(null));
    }

    @Override
    public List<FileData> findAllDocument() {
        return fileRepository.findAll();
    }

    @Override
    public FileData addDocument(MultipartFile file) {
        FileData fileData = new FileData(file);
        fileData.setPath(fileStorageService.storeDocument(file));
        return fileRepository.save(fileData);
    }

    @Override
    public FileData updateDocument(Long id, MultipartFile file) {
        Optional<FileData> fileData = Optional.ofNullable(fileRepository.findById(id)
                .orElse(null));
        FileData updatedFile = null;
        if (null != fileData) {
            this.deleteDocument(fileData.get());
            updatedFile = this.addDocument(file);
        }
        return updatedFile;
    }

    @Override
    public void deleteDocument(FileData fileData) {
        fileStorageService.deleteFile(fileData.getPath());
        fileRepository.delete(fileData);
    }
}
