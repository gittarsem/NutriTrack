package com.project.nutriTrack.dto;

import lombok.Data;

@Data
public class WeeklyUpdateRequest {
    private Double current_daily_target_calories;
    private Double total_consumed_week;
    private Double total_needed_week;
}
