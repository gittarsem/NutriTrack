package com.project.nutriTrack.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", "da837ys3f",
                "api_key", "976398848832232",
                "api_secret", "2Qem_PBBh2mqo0BJSdPrtCjF-UI"
        ));
    }
}

