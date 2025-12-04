package com.project.nutriTrack.controller;

import com.project.nutriTrack.model.User;
import com.project.nutriTrack.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class DbTestController {

    @GetMapping("/write")
    public ResponseEntity<?> write(Authentication authentication) {

        System.out.println(">>> AUTH OBJECT = " + authentication);

        if (authentication == null) {
            return ResponseEntity.status(403).body("NO AUTH RECEIVED");
        }

        return ResponseEntity.ok("AUTH OK for user: " + authentication.name());
    }
}
