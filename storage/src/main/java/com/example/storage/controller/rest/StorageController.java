package com.example.storage.controller.rest;

import com.example.storage.service.Response;
import com.example.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("/file/{id}")
    public Response get(@PathVariable String id) throws Exception {
        try {
            return storageService.GetFile(id);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @PostMapping("/file/upload")
    public String post(@RequestParam("file") MultipartFile file, @RequestParam("type") String outType) throws Exception {
        try {
            return storageService.UploadFile(file, outType);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

}
