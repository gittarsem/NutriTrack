package com.project.nutriTrack.controller;

import com.project.nutriTrack.dto.ProfileUpdateRequest;
import com.project.nutriTrack.model.User;
import com.project.nutriTrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // -------------------------
    // 1. GET PROFILE (for dashboard)
    // -------------------------
    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {

        String email = authentication.getName();  // JWT subject is email

        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userData.get());
    }

    // -------------------------
    // 2. UPDATE PROFILE
    // -------------------------
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            Authentication authentication) {

        String email = authentication.getName(); // Extract email from token

        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userData.get();

        // Only update fields that are present (null-safe update)
        if (request.getName() != null) user.setName(request.getName());
        if (request.getWeight() != null) user.setWeight(request.getWeight());
        if (request.getHeight() != null) user.setHeight(request.getHeight());
        if (request.getTargetWeight() != null) user.setTargetWeight(request.getTargetWeight());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getDietaryPreference() != null) user.setDietaryPreference(request.getDietaryPreference());
        if (request.getActivityLevel() != null) user.setActivityLevel(request.getActivityLevel());

        userRepository.save(user);

        return ResponseEntity.ok("User profile updated successfully!");
    }
}
