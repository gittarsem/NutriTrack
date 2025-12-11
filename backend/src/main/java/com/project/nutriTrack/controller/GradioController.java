package com.project.nutriTrack.controller;

import com.project.nutriTrack.service.GradioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GradioController {

    private final GradioService gradioService;

    @GetMapping("/analyze")
    public Mono<String> analyze(@RequestParam String imageUrl) {
        return gradioService.analyzeImage(imageUrl);
    }
}
