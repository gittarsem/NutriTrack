package com.project.nutriTrack.service;

import com.project.nutriTrack.dto.CalorieTargetRequest;
import com.project.nutriTrack.dto.CalorieTargetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CalorieTargetMLService {

    private final WebClient webClient;

    @Value("${ml.calorie-target.url}")
    private String mlUrl;

    public Mono<CalorieTargetResponse> getCalorieTarget(CalorieTargetRequest req) {

        return webClient.post()
                .uri(mlUrl)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CalorieTargetResponse.class);
    }
}
