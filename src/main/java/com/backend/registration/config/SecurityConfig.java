package com.backend.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔒 Disable CSRF for easier testing (esp. via Postman)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/student/signup",     // ✅ Allow student signup
                                "/api/auth/**"             // ✅ Allow any other public auth APIs
                        ).permitAll()
                        .anyRequest().authenticated() // 🔐 All other endpoints require authentication
                )
                .httpBasic(withDefaults()); // Enable basic auth

        return http.build();
    }
}
