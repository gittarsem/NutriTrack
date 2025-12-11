package com.project.nutriTrack.dto;

import lombok.Data;

@Data
public class CalorieTargetRequest {
    private int age;
    private double weight_kg;
    private double height_cm;
    private String gender;
    private String activity_level;
    private double target_weight_kg;
    private int time_weeks;
}
