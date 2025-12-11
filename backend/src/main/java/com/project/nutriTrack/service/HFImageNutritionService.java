package com.project.nutriTrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HFImageNutritionService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String analyzeFoodImage(String imageUrl) {

        String step1Url = "https://srgv-diet-planner-nutrition.hf.space/gradio_api/call/predict";

        // Prepare JSON body
        Map<String, Object> requestBody = Map.of(
                "data", List.of(
                        Map.of(
                                "path", imageUrl,
                                "meta", Map.of("_type", "gradio.FileData")
                        )
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // STEP 1 → POST → get event_id
        String eventResponse = restTemplate.postForObject(step1Url, entity, String.class);
        System.out.println("STEP 1 Response: " + eventResponse);

        if (eventResponse == null || !eventResponse.contains("event_id")) {
            return "Invalid HF response: " + eventResponse;
        }

        // Extract event_id
        String eventId = eventResponse.split(":")[1]
                .replace("}", "")
                .replace("\"", "")
                .trim();

        System.out.println("Event ID: " + eventId);

        // STEP 2 → GET final prediction
        String step2Url = "https://srgv-diet-planner-nutrition.hf.space/gradio_api/call/predict/" + eventId;

        HttpHeaders step2Headers = new HttpHeaders();
        step2Headers.setAccept(List.of(MediaType.TEXT_EVENT_STREAM));

        HttpEntity<Void> step2Entity = new HttpEntity<>(step2Headers);

        ResponseEntity<String> predictionResponse =
                restTemplate.exchange(step2Url, HttpMethod.GET, step2Entity, String.class);

        String raw = predictionResponse.getBody();
        System.out.println("RAW SSE: " + raw);

        // Extract only the data lines (the actual JSON array)
        String[] lines = raw.split("\\R");
        StringBuilder dataPayload = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("data:")) {
                dataPayload.append(line.substring(5).trim());
            }
        }

        String finalJson = dataPayload.toString();
        System.out.println("FINAL JSON = " + finalJson);

        return finalJson;
    }
}
