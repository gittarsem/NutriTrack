package com.project.nutriTrack.service;

import com.project.nutriTrack.model.User;
import com.project.nutriTrack.repository.UserRepository;
import com.project.nutriTrack.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Annotation ensures Spring finds this component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository; // Your MongoDB repository

    // This is the core method Spring Security calls
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the user by email (as email is used for login)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        // Use the static factory method to create the security object
        return UserDetailsImpl.build(user);
    }
}