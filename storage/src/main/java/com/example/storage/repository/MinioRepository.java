package com.example.storage.repository;

import com.example.storage.service.IStorageRepository;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Repository
@Qualifier("minio")
public class MinioRepository implements IStorageRepository {
    private static final String bucketName = "files";
    private static final String key = "minioadmin";
    private static final String endpoint = "http://localhost:9000";

    public String save(MultipartFile file) {

        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(key, key)
                .build();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Minio", e);
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException ignored) {}
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .expiry(60, TimeUnit.SECONDS)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned URL for object", e);
        }
    };
}
