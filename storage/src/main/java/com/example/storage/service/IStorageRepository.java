package com.example.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageRepository {
    String save(MultipartFile file);
}
