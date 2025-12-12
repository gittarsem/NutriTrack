package com.project.nutriTrack.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.nutriTrack.service.HFImageNutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

            // HF Analysis
            String hfResponse = hfService.analyzeFoodImage(imageUrl);

            return ResponseEntity.ok(Map.of(
                    "imageUrl", imageUrl,
                    "prediction", hfResponse
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("❌ Server Error: " + e.getMessage());
        }
    }


}
