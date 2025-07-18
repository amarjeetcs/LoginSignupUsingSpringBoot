package com.backend.registration.service;

import com.backend.registration.dto.AdminDTO;
import com.backend.registration.entity.Admin;
import com.backend.registration.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(AdminDTO dto) {
        if (adminRepository.existsByEmail(dto.getEmail())) {
            return false; // Email already registered
        }

        Admin admin = new Admin();
        admin.setFullName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setAccountType(dto.getAccountType());
        admin.setCountry(dto.getCountry());
        admin.setPhone(dto.getPhone());
        admin.setTimeZone(dto.getTimeZone());

        adminRepository.save(admin);
        return true;
    }
}
