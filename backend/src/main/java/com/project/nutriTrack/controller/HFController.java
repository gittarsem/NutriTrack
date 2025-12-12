package com.project.nutriTrack.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.nutriTrack.model.Meal;
import com.project.nutriTrack.repository.MealRepository;
import com.project.nutriTrack.service.HFImageNutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
public class HFController {

    private final MealRepository mealLogRepository;
    private final HFImageNutritionService hfService;
    private final Cloudinary cloudinary;

    @PostMapping(value = "/food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzeFood(@RequestPart("file") MultipartFile file, Authentication authentication) {
        try {
            String userId = authentication.getName(); // ✅ Logged-in user ID / email

            // Upload image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "nutriTrack_uploads")
            );

            String imageUrl = uploadResult.get("secure_url").toString();

            // Call HuggingFace model
            String hfResponse = hfService.analyzeFoodImage(imageUrl);

            // Extract JSON part from HF response
            int start = hfResponse.indexOf("{");
            int end = hfResponse.lastIndexOf("}") + 1;
            String jsonPart = hfResponse.substring(start, end);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> parsed = mapper.readValue(jsonPart, Map.class);

            String predictedLabel = (String) parsed.get("predicted_label");
            Double score = ((Number) parsed.get("score")).doubleValue();

            Map<String, Number> nutrition = (Map<String, Number>) parsed.get("nutrition");

            Double calories = nutrition.get("calories").doubleValue();
            Double protein = nutrition.get("protein").doubleValue();
            Double carbs = nutrition.get("carbs").doubleValue();
            Double fat = nutrition.get("fat").doubleValue();

            // ✅ Save Meal in DB BEFORE returning response
            Meal meal = new Meal();
            meal.setUserId(userId);
            meal.setName(predictedLabel);
            meal.setCalories(calories);
            meal.setProtein(protein);
            meal.setCarbs(carbs);
            meal.setFat(fat);
            meal.setImageUrl(imageUrl);

            mealLogRepository.save(meal);

            return ResponseEntity.ok(Map.of(
                    "imageUrl", imageUrl,
                    "prediction_raw", hfResponse,
                    "predicted_label", predictedLabel,
                    "calories", calories,
                    "protein", protein,
                    "carbs", carbs,
                    "fat", fat,
                    "confidence", score
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("❌ Server Error: " + e.getMessage());
        }
    }
}
