package com.krish.photo_project.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String upload(MultipartFile file) throws IOException {

        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of("resource_type", "image")
        );

        return result.get("secure_url").toString();
    }
}
