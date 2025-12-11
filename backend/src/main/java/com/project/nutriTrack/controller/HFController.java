package com.project.nutriTrack.controller;

import com.project.nutriTrack.service.HFImageNutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ml")
public class HFController {

    @Autowired
    private HFImageNutritionService hfService;

    @PostMapping(value = "/food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzeFood(@RequestPart("image") MultipartFile image) {
        try {
            String result = hfService.analyzeFoodImage(image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error calling HuggingFace ML Model: " + e.getMessage());
        }
    }
}
