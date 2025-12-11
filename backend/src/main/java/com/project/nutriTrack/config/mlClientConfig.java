package com.project.nutriTrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class mlClientConfig {

    @Bean("mlWebClient")
    public WebClient mlWebClient() {
        return WebClient.builder().build();
    }

}
