package com.example.file.manager.controller;

import com.example.file.manager.model.FileData;
import com.example.file.manager.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/content/{id}")
    public ResponseEntity<String> findContent(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(fileService.getContent(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FileData> findById(@PathVariable Long id) {
        Optional<FileData> fileData = fileService.findById(id);
        if (fileData.isPresent()) {
            return new ResponseEntity<>(fileData.get(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("No records Found for id :" + id);
        }
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<FileData>> getFileDataList() {
        return new ResponseEntity<>(fileService.findAllDocument(), HttpStatus.OK);
    }

    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileData> uploadDocument(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.addDocument(file), HttpStatus.CREATED);
    }

    @PostMapping(path = "/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileData> updateDocument(@PathVariable Long id, @RequestParam(value = "file", required = true) MultipartFile file) {
        Optional<FileData> fileData = fileService.findById(id);
        if (fileData.isPresent()) {
            return new ResponseEntity<>(fileService.updateDocument(id, file), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("No records Found for id :" + id);
        }
    }

    @DeleteMapping(path = "/{id}")
    public Map<String, String> deleteDocument(@PathVariable(value = "id") final Long id) {
        Optional<FileData> fileData = fileService.findById(id);
        if (fileData.isPresent()) {
            fileService.deleteDocument(fileData.get());

            Map<String, String> response = new HashMap<>();
            response.put("deleted ", fileData.get().getName());
            return response;
        } else {
            throw new EntityNotFoundException("No records Found for id :" + id);
        }
    }
}
