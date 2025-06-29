package com.backend.registration.controller;

import com.backend.registration.dto.StudentDTO;
import com.backend.registration.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid StudentDTO studentDTO) {
        Map<String, String> response = new HashMap<>();

        boolean registered = studentService.register(studentDTO);
        if (registered) {
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
