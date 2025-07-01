package com.lms.controller;

import com.lms.entity.User;
import com.lms.service.AuthService;
import com.lms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String result = authService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login/email")
    public ResponseEntity<?> loginWithEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required."));
        }

        Optional<User> user = authService.loginWithEmail(email, password);
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "user", Map.of(
                    "id", user.get().getId(),
                    "email", user.get().getEmail(),
                    "role", user.get().getRole()  // Corrected role here
                )
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }
    }

    @PostMapping("/login/phone")
    public ResponseEntity<?> loginWithPhone(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String password = body.get("password");

        if (phone == null || password == null || phone.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Phone and password are required."));
        }

        Optional<User> user = authService.loginWithPhone(phone, password);
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(phone); // or use email if preferred
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "user", Map.of(
                    "id", user.get().getId(),
                    "phone", user.get().getPhone(),
                    "role", user.get().getRole()  // Corrected role here
                )
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid phone or password"));
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String identifier = request.get("identifier");

        if (identifier == null || identifier.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email or phone is required."));
        }

        Optional<User> user = authService.findByEmailOrPhone(identifier);
        if (user.isPresent()) {
            // TODO: Generate OTP/email with token (mocked for now)
            return ResponseEntity.ok(Map.of("message", "Password reset link or OTP sent to your contact."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "User not found."));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String identifier = request.get("identifier");
        String newPassword = request.get("newPassword");

        if (identifier == null || newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Identifier and new password are required."));
        }

        boolean updated = authService.resetPassword(identifier, newPassword);
        if (updated) {
            return ResponseEntity.ok(Map.of("message", "Password reset successful."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "User not found."));
        }
    }
}
