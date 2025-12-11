package com.project.nutriTrack.dto;

import lombok.Data;

@Data
public class WeeklyUpdateRequest {
    private double current_daily_target_calories;
    private double total_consumed_week;
    private double total_needed_week;
}
