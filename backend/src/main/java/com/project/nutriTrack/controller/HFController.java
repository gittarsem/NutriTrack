package com.project.nutriTrack.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.nutriTrack.service.HFImageNutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
public class HFController {

    private final HFImageNutritionService hfService;
    private final Cloudinary cloudinary;

    @PostMapping("/food")
    public ResponseEntity<?> analyzeFood(@RequestPart("file") MultipartFile file) {
        try {
            System.out.println("📥 Received file: " + file.getOriginalFilename());

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "nutriTrack_uploads")
            );

            String imageUrl = uploadResult.get("secure_url").toString();
            System.out.println("☁️ Cloudinary URL: " + imageUrl);

            // Send URL to HF model
            String hfResponse = hfService.analyzeFoodImage(imageUrl);

            return ResponseEntity.ok(Map.of(
                    "imageUrl", imageUrl,
                    "prediction", hfResponse
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Server Error: " + e.getMessage());
        }
    }
}
