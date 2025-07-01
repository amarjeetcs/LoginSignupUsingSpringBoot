package com.lms.service;

import com.lms.entity.User;
import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use Spring-managed bean

    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public Optional<User> loginWithEmail(String email, String password) {
        return userRepository.findByEmail(email)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public Optional<User> loginWithPhone(String phone, String password) {
        return userRepository.findByPhone(phone)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public Optional<User> findByEmailOrPhone(String identifier) {
        Optional<User> user = userRepository.findByEmail(identifier);
        if (user.isEmpty()) {
            user = userRepository.findByPhone(identifier);
        }
        return user;
    }

    public boolean resetPassword(String identifier, String newPassword) {
        Optional<User> user = findByEmailOrPhone(identifier);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}

