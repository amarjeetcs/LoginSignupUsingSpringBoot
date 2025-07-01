package com.backend.login.service;

import com.backend.registration.entity.Student;
import com.backend.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                student.getEmail(),
                student.getPassword(),
                new ArrayList<>() 
        );
    }
}
