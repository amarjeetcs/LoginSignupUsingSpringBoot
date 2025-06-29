package com.backend.registration.controller;

import com.backend.registration.dto.OrganizationDTO;
import com.backend.registration.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/organization")
@CrossOrigin(origins = "*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid OrganizationDTO organizationDTO) {
        Map<String, String> response = new HashMap<>();

        boolean registered = organizationService.register(organizationDTO);
        if (registered) {
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
