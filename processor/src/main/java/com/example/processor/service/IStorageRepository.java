package com.example.processor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IStorageRepository {
    String save(File file);
}
