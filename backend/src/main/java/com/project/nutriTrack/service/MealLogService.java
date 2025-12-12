package com.project.nutriTrack.service;

import com.project.nutriTrack.model.DailySummary;
import com.project.nutriTrack.model.Meal;
import com.project.nutriTrack.repository.DailySummaryRepository;
import com.project.nutriTrack.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealLogService {

    private final MealRepository mealRepository;
    private final DailySummaryRepository dailySummaryRepository;

    public void addMeal(String userId, Meal meal) {

        meal.setUserId(userId);

        // 1️⃣ Save meal to DB
        mealRepository.save(meal);

        // 2️⃣ Fetch today's DailySummary
        Optional<DailySummary> summaryOpt =
                dailySummaryRepository.findByUserIdAndDate(userId, LocalDate.now());

        if (summaryOpt.isEmpty()) return;

        DailySummary summary = summaryOpt.get();

        // 3️⃣ Update daily consumed
        double newConsumed = summary.getDailyConsumed() + meal.getCalories();
        summary.setDailyConsumed(newConsumed);

        // 4️⃣ Update remaining
        summary.setRemaining(summary.getDailyTargetCalories() - newConsumed);

        dailySummaryRepository.save(summary);
    }
}
