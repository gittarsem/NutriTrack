package com.project.nutriTrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GradioService {

    private final WebClient webClient;

    private static final String BASE_URL = "https://srgv-diet-planner-nutrition.hf.space";

    public Mono<String> analyzeImage(String imageUrl) {

        // Step 1: Build request body (same as your curl data array)
        Map<String, Object> body = Map.of(
                "data", new Object[] {
                        Map.of(
                                "path", imageUrl,
                                "meta", Map.of("_type", "gradio.FileData")
                        )
                }
        );

        // Step 2: Make the initial prediction call
        return webClient.post()
                .uri(BASE_URL + "/gradio_api/call/predict")
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {

                    // Extract the event ID (same as your awk)
                    String eventId = (String) response.get("event_id");
                    System.out.println("Event ID = " + eventId);

                    // Step 3: Poll the result endpoint using event_id
                    return webClient.get()
                            .uri(BASE_URL + "/gradio_api/call/predict/" + eventId)
                            .retrieve()
                            .bodyToMono(String.class);
                });
    }
}
