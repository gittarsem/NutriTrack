package com.project.nutriTrack.service;

import com.project.nutriTrack.model.Meal;
import com.project.nutriTrack.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public List<Meal> getMealsForUser(String userId) {
        return mealRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
