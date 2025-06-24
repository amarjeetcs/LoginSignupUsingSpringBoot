package com.backend.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String status() {
        return "✅ Application is up and running!";
    }

    @GetMapping("/hello")
    public String getName()
    {
        return "Hello Amarjeet";
    }
}