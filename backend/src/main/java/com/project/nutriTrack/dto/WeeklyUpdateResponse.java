package com.project.nutriTrack.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WeeklyUpdateResponse {
    private Double adjustment_per_day;
    private Map<String, Double> meal_breakdown;
    private Double new_daily_target;
    private Double previous_daily_target;
    private Double weekly_consumed;
    private Double weekly_diff;
    private Double weekly_needed;
}
