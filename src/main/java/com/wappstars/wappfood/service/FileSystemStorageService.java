package com.wappstars.wappfood.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;

import com.wappstars.wappfood.config.FileUploadProperties;
import com.wappstars.wappfood.exception.FileNotFoundException;
import com.wappstars.wappfood.exception.FileStorageException;
import com.wappstars.wappfood.exception.FileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements IFileSytemStorage {
    private final Path dirLocation;
    @Autowired
    public FileSystemStorageService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.dirLocation);
        }
        catch (Exception ex) {
            throw new FileStorageException("Could not create upload dir!");
        }
    }

    @Override
    public String saveFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName);

        if(extension.equals("png") || extension.equals("jpg")) {
            try {
                Path dfile = this.dirLocation.resolve(fileName);
                Files.copy(file.getInputStream(), dfile, StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            } catch (Exception ex) {
                throw new FileStorageException("Could not upload file");
            }
        } else {
            throw new FileTypeException("Only JPG or PNG allowed");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException("Could not find file");
            }
        }
        catch (MalformedURLException ex) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
