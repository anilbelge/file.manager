package com.example.file.manager.services;

import com.example.file.manager.exceptions.ApiException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String dir = "uploads";
    private static Path storageDir;
    private final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @PostConstruct
    public void initStorageDirectory() {
        this.storageDir = Paths.get(dir);
        if (!Files.exists(this.storageDir)) {
            try {
                Files.createDirectories(this.storageDir);
            } catch (final IOException exception) {
                String message = "Error creating storage directory";
                logger.error(message, exception);
                throw new ApiException(message, exception);
            }
        }
    }

    @Override
    public String storeDocument(final MultipartFile file) {
        Path filePath = this.storageDir.resolve(UUID.randomUUID() + File.separator + file.getOriginalFilename());
        String absolutePathStr = filePath.toAbsolutePath().toString();
        final Long maxFileSize = 5000000L;
        final String[] validExtensions = {"png", "jpeg", "jpg", "docx", "pdf", "xlsx"};
        try {
            if (file.isEmpty()) {
                String msg = "File can not be empty:" + file.getOriginalFilename();
                logger.error(msg);
                throw new ApiException(msg);
            }
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double megabytes = (kilobytes / 1024);

            if (maxFileSize < file.getSize()) {
                String msg = "The file size cannot be more than 5 MB , the size of your file is :: " + megabytes;
                logger.error(msg);
                throw new ApiException(msg);
            }

            Optional<String> optional = Arrays.stream(validExtensions).filter(f -> f.equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))).findFirst();
            if (!optional.isPresent()) {
                String msg = "File extension is not valid for file :: " + file.getOriginalFilename();
                logger.error(msg);
                throw new ApiException(msg);
            }

            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            logger.info("Saving the form file to:" + absolutePathStr);
            Files.copy(file.getInputStream(), filePath);
            return absolutePathStr;
        } catch (final IOException exception) {
            throw new ApiException("Error saving file to " + absolutePathStr, exception);
        }
    }

    public void deleteFile(String path) {
        try {
            File deleteFile = new File(path);
            if (deleteFile.exists()) {
                FileUtils.forceDelete(deleteFile);
            }
        } catch (Throwable t) {
            throw new ApiException("Error cleaning file " + path, t);
        }
    }
}
