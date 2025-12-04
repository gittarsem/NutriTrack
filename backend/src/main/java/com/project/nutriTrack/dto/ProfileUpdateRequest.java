package com.project.nutriTrack.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Data
public class ProfileUpdateRequest {
    @NotBlank
    private String name;

    @NotNull @Min(1)
    private Double weight;

    @NotNull @Min(1)
    private Double height;

    @NotNull @Min(1)
    private Double targetWeight;

    @NotBlank
    private String gender;

    @NotBlank
    private String dietaryPreference;

    @NotBlank
    private String activityLevel;


}