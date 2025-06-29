package com.backend.registration.service;

import com.backend.registration.dto.InstructorDTO;
import com.backend.registration.entity.Instructor;
import com.backend.registration.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(InstructorDTO dto) {
        if (instructorRepository.existsByEmail(dto.getEmail())) {
            return false; // Email already used by another instructor
        }

        Instructor instructor = new Instructor();
        instructor.setFullName(dto.getFullName());
        instructor.setEmail(dto.getEmail());
        instructor.setPassword(passwordEncoder.encode(dto.getPassword()));
        instructor.setAccountType(dto.getAccountType());
        instructor.setCountry(dto.getCountry());
        instructor.setPhone(dto.getPhone());
        instructor.setTimeZone(dto.getTimeZone());

        instructorRepository.save(instructor);
        return true;
    }
}
