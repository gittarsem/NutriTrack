package com.project.nutriTrack.controller;

import com.cloudinary.Cloudinary;
import com.project.nutriTrack.service.GradioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {

    private final GradioService gradioService;
    private final Cloudinary cloudinary;

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {

        // 1️⃣ Upload image to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of("folder", "nutriTrack_uploads")
        );

        // 2️⃣ Public image URL
        String imageUrl = (String) uploadResult.get("secure_url");

        System.out.println("Uploaded to Cloudinary: " + imageUrl);

        // 3️⃣ Pass the URL to HuggingFace ML Service
        return gradioService.analyzeImage(imageUrl);
    }
}

