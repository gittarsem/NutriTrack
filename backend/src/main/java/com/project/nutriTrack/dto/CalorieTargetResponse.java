package com.project.nutriTrack.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CalorieTargetResponse {
    private double daily_target_calories;
    private Map<String, Double> meal_breakdown;
    private double maintenance_calories;
    private double daily_change_needed;
    private String suggestion;

    private Macros macros; // <-- Only calories + grams
}

@Data
class Macros {
    private MacroValue protein;
    private MacroValue fat;
    private MacroValue carbs;
}

@Data
class MacroValue {
    private double calories;
    private double grams;
}
