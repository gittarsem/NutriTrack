package com.project.nutriTrack.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;
    private Double weight;
    private Double height;
    private Double targetWeight;
    private String gender;
    private String dietaryPreference;
    private String activityLevel;
    private Double age;
    private Double timesWeek;
}
