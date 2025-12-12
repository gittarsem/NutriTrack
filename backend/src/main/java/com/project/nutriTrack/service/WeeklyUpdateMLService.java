package com.project.nutriTrack.service;

import com.project.nutriTrack.dto.WeeklyUpdateRequest;
import com.project.nutriTrack.dto.WeeklyUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeeklyUpdateMLService {

    private final WebClient webClient;

    @Value("${ml.weekly-url}")
    private String weeklyUrl;

    public Mono<WeeklyUpdateResponse> getWeeklyUpdate(WeeklyUpdateRequest req) {
        return webClient.post()
                .uri(weeklyUrl)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(WeeklyUpdateResponse.class);
    }
}
