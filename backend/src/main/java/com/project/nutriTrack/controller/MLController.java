package com.project.nutriTrack.controller;

import com.project.nutriTrack.dto.*;
import com.project.nutriTrack.service.MLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
public class MLController {

    private final MLService mlService;

    @PostMapping("/calorie-target")
    public ResponseEntity<CalorieTargetResponse> calorieTarget(@RequestBody CalorieTargetRequest request) {
        return ResponseEntity.ok(mlService.getCalorieTarget(request));
    }

    @PostMapping("/weekly-update")
    public ResponseEntity<WeeklyUpdateResponse> weeklyUpdate(@RequestBody WeeklyUpdateRequest request) {
        return ResponseEntity.ok(mlService.getWeeklyUpdate(request));
    }
}
