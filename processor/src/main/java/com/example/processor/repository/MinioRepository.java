package com.example.processor.repository;

import com.example.processor.service.IStorageRepository;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Repository
@Qualifier("minio")
public class MinioRepository implements IStorageRepository {
    private static final String bucketName = "done";
    private static final String key = "minioadmin";
    private static final String endpoint = "http://localhost:9000";

    public String save(File file) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(key, key)
                .build();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            var in = new FileInputStream(file);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getName())
                            .stream(in, in.available(), -1)
                            .build());
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Minio", e);
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(file.getName())
                            .expiry(3600, TimeUnit.SECONDS)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned URL for object", e);
        }
    };
}
