package com.example.storage.service;

import com.example.storage.model.File;
import com.example.storage.model.UploadFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class StorageService {

  @Autowired
  private IDatabaseRepository db;

  @Autowired
  @Qualifier("minio")
  private IStorageRepository storage;

  @Autowired
  @Qualifier("kafka")
  private IMomRepository mom;

  public Response GetFile(String id) {
    var file = db.findById(UUID.fromString(id)).get();
    return new Response(file.getStatus(), file.getUrl());
  }

  public String UploadFile(MultipartFile file, String outType) {
    var type = file.getOriginalFilename().substring(
            file.getOriginalFilename().lastIndexOf('.')+1
    );

    if (!type.equals("png") && !type.equals("jpeg")) {
      throw new RuntimeException("Wrong file type");
    }

    var uploadFile = new File(
            file.getOriginalFilename(),
            Enum.valueOf(UploadFileType.class, type),
            file.getSize(),
            new Date(),
            false,
            ""
    );
    var meta = db.save(uploadFile);
    var link = storage.save(file);
    mom.send(meta.getId().toString(), link, type, outType);

    return meta.getId().toString();
  }

  public void updateFileMeta(String url, String id) {
    db.updateStatusById(url, UUID.fromString(id));
  }
}
