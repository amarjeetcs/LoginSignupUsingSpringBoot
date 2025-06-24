//package com.backend.registration.service;
//
//import com.backend.registration.entity.User;
//import com.backend.registration.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public User register(User user) {
//        return userRepository.save(user);
//    }
//
//    public User login(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        return null;
//    }
//}
//with dto code

package com.backend.registration.service;

import com.backend.registration.dto.UserDTO;
import com.backend.registration.entity.User;
import com.backend.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register user with email check
    public boolean register(UserDTO userDTO) {
        // Check if user with same email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return false; // Duplicate email
        }

        // Optional: Check if passwords match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return false; // Password mismatch
        }

        // Convert DTO to Entity
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setConfirmPassword(userDTO.getConfirmPassword());

        userRepository.save(user);
        return true;
    }

    // ✅ Login logic
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
