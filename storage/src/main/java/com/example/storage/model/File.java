package com.example.storage.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid4")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UploadFileType type;
    @Column(name = "size")
    private Long size;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "url")
    private String url;

    protected File() {}

    public File(String name, UploadFileType type, Long size, Date createdAt, Boolean status, String url) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.createdAt = createdAt;
        this.status = status;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(UploadFileType type) {
        this.type = type;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getSize() {
        return size;
    }

    public UploadFileType getType() {
        return type;
    }
}


