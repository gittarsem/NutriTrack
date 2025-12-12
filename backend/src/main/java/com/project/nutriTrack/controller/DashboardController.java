package com.project.nutriTrack.controller;

import com.project.nutriTrack.model.DailySummary;
import com.project.nutriTrack.repository.DailySummaryRepository;
import com.project.nutriTrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;


@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    @Autowired
    private UserRepository userRepository;

    private final DailySummaryRepository dailySummaryRepository;

    @GetMapping("/today")
    public ResponseEntity<?> getTodaySummary(Authentication auth) {
        String email = auth.getName();
        String userId = userRepository.findByEmail(email).get().getId();


        Optional<DailySummary> opt = dailySummaryRepository.findByUserIdAndDate(userId, LocalDate.now());
        if (opt.isEmpty()) {
            return ResponseEntity.ok("No summary for today");
        }
        return ResponseEntity.ok(opt.get());
    }
}
