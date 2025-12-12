package com.project.nutriTrack.controller;

import com.project.nutriTrack.model.Meal;
import com.project.nutriTrack.repository.MealRepository;
import com.project.nutriTrack.repository.UserRepository;
import com.project.nutriTrack.service.MealLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal")
public class MealController {

    @Autowired
    private MealLogService mealLogService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/log")
    public ResponseEntity<?> logMeal(@RequestBody Meal meal, Authentication auth) {

        String email = auth.getName();
        String userId = userRepository.findByEmail(email).get().getId();
        // email or ID from JWT

        mealLogService.addMeal(userId, meal);

        return ResponseEntity.ok("Meal logged successfully!");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Meal>> getHistory(Authentication auth) {
        String userId = auth.getName();
        List<Meal> meals = mealRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return ResponseEntity.ok(meals);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllMeals(Authentication authentication) {
        String userEmail = authentication.getName(); // 👈 real logged-in user email
        List<Meal> meals = mealRepository.findByUserIdOrderByCreatedAtDesc(userEmail);
        return ResponseEntity.ok(meals);
    }

}
