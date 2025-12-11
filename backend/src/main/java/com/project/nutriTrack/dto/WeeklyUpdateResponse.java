package com.project.nutriTrack.dto;

import lombok.Data;
import java.util.Map;

@Data
public class WeeklyUpdateResponse {
    private double previous_daily_target;
    private double weekly_consumed;
    private double weekly_needed;
    private double weekly_diff;
    private double adjustment_per_day;
    private double new_daily_target;
    private Map<String, Double> meal_breakdown;
}
