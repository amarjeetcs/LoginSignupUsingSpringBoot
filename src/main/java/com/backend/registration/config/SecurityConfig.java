package com.backend.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Global CORS configuration bean
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow your frontend origins here
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));

        // Allow standard HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        config.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this config for all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS support in Spring Security
                .and()
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing convenience
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/student/signup",
                                "/api/instructor/signup",
                                "/api/organization/signup",
                                "/api/admin/signup",
                                "/api/auth/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
