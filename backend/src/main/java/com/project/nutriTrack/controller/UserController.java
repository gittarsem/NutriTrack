package com.project.nutriTrack.controller;

import com.project.nutriTrack.dto.CalorieTargetRequest;
import com.project.nutriTrack.dto.CalorieTargetResponse;
import com.project.nutriTrack.dto.ProfileUpdateRequest;
import com.project.nutriTrack.model.User;
import com.project.nutriTrack.repository.UserRepository;
import com.project.nutriTrack.service.CalorieTargetMLService;
import com.project.nutriTrack.service.DailySummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalorieTargetMLService calorieTargetMLService;

    @Autowired
    private DailySummaryService dailySummaryService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // ------------------------------------
    // GET USER PROFILE
    // ------------------------------------
    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {

        String email = authentication.getName();
        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userData.get());
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ADD THIS LINE HERE 👇👇👇
        user.setUsername(user.getEmail().split("@")[0]);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // ------------------------------------
    // UPDATE PROFILE + TRIGGER ML API
    // ------------------------------------
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userData.get();

        if (request.getName() != null) user.setName(request.getName());
        if (request.getWeight() != null) user.setWeight(request.getWeight());
        if (request.getHeight() != null) user.setHeight(request.getHeight());
        if (request.getTargetWeight() != null) user.setTargetWeight(request.getTargetWeight());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getDietaryPreference() != null) user.setDietaryPreference(request.getDietaryPreference());
        if (request.getActivityLevel() != null) user.setActivityLevel(request.getActivityLevel());
        if (request.getTimesWeek() != null) user.setTimesWeek(request.getTimesWeek());
        if (request.getAge() != null) user.setAge(request.getAge());

        userRepository.save(user);

        CalorieTargetRequest mlReq = new CalorieTargetRequest();
        mlReq.setAge(user.getAge());
        mlReq.setWeight_kg(user.getWeight());
        mlReq.setHeight_cm(user.getHeight());
        mlReq.setGender(user.getGender());
        mlReq.setActivity_level(user.getActivityLevel());
        mlReq.setTarget_weight_kg(user.getTargetWeight());
        mlReq.setTime_weeks(user.getTimesWeek());

        // BLOCKING CALL – REQUIRED IN MVC
        CalorieTargetResponse mlResponse =
                calorieTargetMLService.getCalorieTarget(mlReq).block();

        dailySummaryService.saveCalorieTarget(user.getId(), mlResponse);

        return ResponseEntity.ok(mlResponse);
    }

}
