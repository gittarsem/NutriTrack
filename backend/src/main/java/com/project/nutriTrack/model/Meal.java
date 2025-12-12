package com.project.nutriTrack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document("meals")
public class Meal {

    @Id
    private String id;

    private String userId;

    private String name;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}
