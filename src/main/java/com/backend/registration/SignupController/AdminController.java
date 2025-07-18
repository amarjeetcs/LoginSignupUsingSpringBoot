package com.backend.registration.SignupController;

import com.backend.registration.dto.AdminDTO;
import com.backend.registration.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid AdminDTO adminDTO) {
        Map<String, String> response = new HashMap<>();

        boolean registered = adminService.register(adminDTO);
        if (registered) {
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
