package com.backend.registration.controller;

import com.backend.registration.dto.InstructorDTO;
import com.backend.registration.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/instructor")
@CrossOrigin(origins = "*")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid InstructorDTO instructorDTO) {
        Map<String, String> response = new HashMap<>();

        boolean registered = instructorService.register(instructorDTO);
        if (registered) {
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
