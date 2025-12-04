package com.project.nutriTrack.repository;

import com.project.nutriTrack.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); // REQUIRED FOR REGISTER

}
