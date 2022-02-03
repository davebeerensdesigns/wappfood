package com.wappstars.wappfood.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
    void init();
    String saveFile(MultipartFile file);
    Resource loadFile(String fileName);
}
