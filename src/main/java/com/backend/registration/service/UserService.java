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

//package com.backend.registration.service;
//
//import com.backend.registration.dto.UserDTO;
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
//
//    public boolean register(UserDTO userDTO) {
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            return false;
//        }
//
//        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
//            return false; // Password mismatch
//        }
//
//        User user = new User();
//        user.setFullName(userDTO.getFullName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setConfirmPassword(userDTO.getConfirmPassword());
//
//        userRepository.save(user);
//        return true;
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

package com.backend.registration.service;

import com.backend.registration.dto.UserDTO;
import com.backend.registration.entity.User;
import com.backend.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return false;
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return false;
        }

        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // 🔒 Encrypted
        user.setConfirmPassword(passwordEncoder.encode(userDTO.getConfirmPassword())); // optional

        userRepository.save(user);
        return true;
    }

    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }
}
