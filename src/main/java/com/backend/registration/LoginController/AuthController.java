package com.backend.registration.LoginController;

import com.backend.registration.entity.Admin;
import com.backend.registration.entity.Instructor;
import com.backend.registration.entity.Organization;
import com.backend.registration.entity.Student;
import com.backend.registration.repository.AdminRepository;
import com.backend.registration.repository.InstructorRepository;
import com.backend.registration.repository.OrganizationRepository;
import com.backend.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class AuthController {

    @Autowired private StudentRepository studentRepo;
    @Autowired private InstructorRepository instructorRepo;
    @Autowired private OrganizationRepository organizationRepo;
    @Autowired private AdminRepository adminRepo;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Student
        Optional<Student> studentOpt = studentRepo.findByEmail(email);
        if (studentOpt.isPresent() && passwordEncoder.matches(password, studentOpt.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "role", "student",
                    "name", studentOpt.get().getFullName()
            ));
        }

        // Instructor
        Optional<Instructor> instructorOpt = instructorRepo.findByEmail(email);
        if (instructorOpt.isPresent() && passwordEncoder.matches(password, instructorOpt.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "role", "instructor",
                    "name", instructorOpt.get().getFullName()
            ));
        }

        // Organization
        Optional<Organization> orgOpt = organizationRepo.findByEmail(email);
        if (orgOpt.isPresent() && passwordEncoder.matches(password, orgOpt.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "role", "organization",
                    "name", orgOpt.get().getFullName()
            ));
        }

        // Admin
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent() && passwordEncoder.matches(password, adminOpt.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "role", "admin",
                    "name", adminOpt.get().getFullName()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid email or password"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam String email) {
        Optional<Student> studentOpt = studentRepo.findByEmail(email);
        if (studentOpt.isPresent()) return ResponseEntity.ok(studentOpt.get());

        Optional<Instructor> instructorOpt = instructorRepo.findByEmail(email);
        if (instructorOpt.isPresent()) return ResponseEntity.ok(instructorOpt.get());

        Optional<Organization> orgOpt = organizationRepo.findByEmail(email);
        if (orgOpt.isPresent()) return ResponseEntity.ok(orgOpt.get());

        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) return ResponseEntity.ok(adminOpt.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "User not found"));
    }
}
