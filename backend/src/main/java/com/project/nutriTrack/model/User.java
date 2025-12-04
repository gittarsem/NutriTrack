package com.project.nutriTrack.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;

    private String name;
    private Double weight;
    private Double height;
    private Double targetWeight;
    private String gender;
    private String dietaryPreference; // vegetarian, non-veg, eggeterian
    private String activityLevel;

}
