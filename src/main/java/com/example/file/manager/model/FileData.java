package com.example.file.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILE_DATA")
public class FileData implements Serializable {
    private static final long serialVersionUID = -2347866078938949077L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PATH")
    private String path;

    @Column(name = "SIZE")
    private Long size;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EXTENSION")
    private String extension;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "CREATION_TIME"
    )
    private Date creationTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "MODIFIED_TIME"
    )
    private Date modifiedTime;

    public FileData(MultipartFile file) {
        this.size = file.getSize();
        this.name = file.getOriginalFilename();
        this.extension = FilenameUtils.getExtension(file.getOriginalFilename());
    }
}
