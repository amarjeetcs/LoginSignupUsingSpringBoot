//package com.backend.registration.controller;
//
//import com.backend.registration.entity.User;
//import com.backend.registration.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*") // Allow frontend to call this
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/signup")
//    public User signup(@RequestBody User user) {
//        return userService.register(user);
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody User loginUser) {
//        User user = userService.login(loginUser.getEmail(), loginUser.getPassword());
//        if (user != null) {
//            return "Login successful";
//        } else {
//            return "Invalid credentials";
//        }
//    }
//}
//below with dto

package com.backend.registration.controller;

import com.backend.registration.dto.UserDTO;
import com.backend.registration.entity.User;
import com.backend.registration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

//    @PostMapping("/signup")
//    public String signup(@RequestBody @Valid UserDTO userDTO) {
//        boolean isRegistered = userService.register(userDTO);
//        if (isRegistered) {
//            return "Signup successful";
//        } else {
//            return "User already exists with this email";
//        }
//    }
///with customize response to user on ui
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();
        if (userService.register(userDTO)) {
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User already exists with this email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }


    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getEmail(), loginUser.getPassword());
        if (user != null) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }
}
