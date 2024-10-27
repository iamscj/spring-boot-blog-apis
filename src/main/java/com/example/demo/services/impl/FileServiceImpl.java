package com.example.demo.services.impl;

import com.example.demo.exceptions.FileFormatException;
import com.example.demo.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException, FileFormatException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".jpeg") && !fileName.toLowerCase().endsWith(".png"))) {
            throw new FileFormatException("Only .jpg and .png images supported");
        }
        String extension = fileName.substring(fileName.lastIndexOf("."));

        String imageName = UUID.randomUUID() + extension;

        //full file path
        String filePath = path + File.separator + imageName;

        //create file if not created
        File newDir = new File(path);
        if (!newDir.exists()) {
            newDir.mkdir();
        }

        // Copy the file to the target location, replacing it if it already exists
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return imageName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;

        return new FileInputStream(fullPath);
    }
}
