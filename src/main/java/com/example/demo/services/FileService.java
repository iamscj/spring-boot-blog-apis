package com.example.demo.services;

import com.example.demo.exceptions.FileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadImage(String path, MultipartFile file) throws IOException, FileFormatException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
