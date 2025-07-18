package com.backend.registration.service;

import com.backend.registration.dto.StudentDTO;
import com.backend.registration.entity.Student;
import com.backend.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(StudentDTO dto) {
        if (studentRepository.existsByEmail(dto.getEmail())) {
            return false;
        }

        Student student = new Student();
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setAccountType(dto.getAccountType());
        student.setCountry(dto.getCountry());
        student.setPhone(dto.getPhone());
        student.setTimeZone(dto.getTimeZone());

        studentRepository.save(student);
        return true;
    }
}
