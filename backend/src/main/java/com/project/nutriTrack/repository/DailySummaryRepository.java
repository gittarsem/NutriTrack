package com.project.nutriTrack.repository;

import com.project.nutriTrack.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {

    Optional<DailySummary> findByUserIdAndDate(String userId, LocalDate date);
}
