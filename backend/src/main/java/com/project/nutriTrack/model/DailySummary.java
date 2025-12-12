package com.project.nutriTrack.model;

import com.project.nutriTrack.dto.Macros;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Data
@Document("daily_summary")
public class DailySummary {

    @Id
    private String id;

    private String userId;

    private LocalDate date;

    private Double dailyTargetCalories;
    private Double maintenanceCalories;
    private Double dailyChangeNeeded;

    private Macros macros;
    private Map<String, Double> mealBreakdown;

    private String suggestion;

    private Double dailyConsumed;
    private Double remaining;
}
