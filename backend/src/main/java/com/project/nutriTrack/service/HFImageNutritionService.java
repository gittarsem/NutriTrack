package com.project.nutriTrack.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class HFImageNutritionService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://srgv-diet-planner-nutrition.hf.space")
            .build();

    public String analyzeFoodImage(MultipartFile image) throws IOException {

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("image", new MultipartInputResource(image));

        return webClient.post()
                .uri("/run/predict")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Converts MultipartFile → Resource for WebClient
    public static class MultipartInputResource extends InputStreamResource {

        private final String filename;
        private final long contentLength;

        public MultipartInputResource(MultipartFile file) throws IOException {
            super(file.getInputStream());
            this.filename = file.getOriginalFilename();
            this.contentLength = file.getSize();
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() {
            return this.contentLength;
        }
    }
}
