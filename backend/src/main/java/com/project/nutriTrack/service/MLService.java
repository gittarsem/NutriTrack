package com.project.nutriTrack.service;

import com.project.nutriTrack.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MLService {

    private final WebClient webClient;

    @Value("${ml.calorie-target.url}")
    private String calorieTargetUrl;

    @Value("${ml.weekly-update.url}")
    private String weeklyUpdateUrl;

    public CalorieTargetResponse getCalorieTarget(CalorieTargetRequest request) {

        System.out.println("Sending request to ML API: " + calorieTargetUrl);
        System.out.println("Request Body: " + request);

        return webClient.post()
                .uri(calorieTargetUrl)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .map(errorBody -> {
                                    System.out.println("ML API returned error body: " + errorBody);
                                    return new RuntimeException("ML API error: " + errorBody);
                                })
                )
                .bodyToMono(CalorieTargetResponse.class)
                .doOnError(err -> {
                    System.out.println("ERROR calling ML API:");
                    err.printStackTrace();
                })
                .block();
    }


    public WeeklyUpdateResponse getWeeklyUpdate(WeeklyUpdateRequest request) {
        return webClient.post()
                .uri(weeklyUpdateUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(WeeklyUpdateResponse.class)
                .block();
    }
}
