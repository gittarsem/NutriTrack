package com.project.nutriTrack.controller;

import com.project.nutriTrack.dto.WeeklyUpdateRequest;
import com.project.nutriTrack.dto.WeeklyUpdateResponse;
import com.project.nutriTrack.model.DailySummary;
import com.project.nutriTrack.repository.DailySummaryRepository;
import com.project.nutriTrack.repository.UserRepository;
import com.project.nutriTrack.service.WeeklyUpdateMLService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/ml")
@RequiredArgsConstructor
public class WeeklyController {
    @Autowired
    private UserRepository userRepository;
    private final WeeklyUpdateMLService weeklyUpdateMLService;
    private final DailySummaryRepository dailySummaryRepository;

    @PostMapping("/weekly-update-v2")
    public Mono<ResponseEntity<WeeklyUpdateResponse>> weeklyUpdate(@RequestBody WeeklyUpdateRequest req, Authentication auth) {

        String email = auth.getName();
        String userId = userRepository.findByEmail(email).get().getId();


        return weeklyUpdateMLService.getWeeklyUpdate(req)
                .flatMap(res -> {
                    // find today's daily summary and update the values
                    Optional<DailySummary> opt = dailySummaryRepository.findByUserIdAndDate(userId, LocalDate.now());
                    DailySummary summary = opt.orElseGet(() -> {
                        DailySummary s = new DailySummary();
                        s.setUserId(userId);
                        s.setDate(LocalDate.now());
                        s.setDailyConsumed(0.0);
                        return s;
                    });

                    summary.setDailyTargetCalories(res.getNew_daily_target());
                    summary.setMealBreakdown(res.getMeal_breakdown());
                    // keep maintenance/dailyChange if you want
                    dailySummaryRepository.save(summary);

                    return Mono.just(ResponseEntity.ok(res));
                })
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.internalServerError().build())
                );
    }
}
