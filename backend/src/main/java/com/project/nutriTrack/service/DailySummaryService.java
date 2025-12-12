package com.project.nutriTrack.service;

import com.project.nutriTrack.dto.CalorieTargetResponse;
import com.project.nutriTrack.model.DailySummary;
import com.project.nutriTrack.repository.DailySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailySummaryService {

    private final DailySummaryRepository dailySummaryRepository;

    public void saveCalorieTarget(String userId, CalorieTargetResponse response) {

        DailySummary summary = new DailySummary();

        summary.setUserId(userId);
        summary.setDate(LocalDate.now());

        summary.setDailyTargetCalories(response.getDaily_target_calories());
        summary.setMaintenanceCalories(response.getMaintenance_calories());
        summary.setDailyChangeNeeded(response.getDaily_change_needed());

        summary.setMacros(response.getMacros());
        summary.setMealBreakdown(response.getMeal_breakdown());
        summary.setSuggestion(response.getSuggestion());

        summary.setDailyConsumed(0.0);
        summary.setRemaining(response.getDaily_target_calories());

        dailySummaryRepository.save(summary);
    }
}
