package com.project.nutriTrack.repository;

import com.project.nutriTrack.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MealRepository extends MongoRepository<Meal, String> {

    List<Meal> findByUserIdOrderByCreatedAtDesc(String userId);
}
