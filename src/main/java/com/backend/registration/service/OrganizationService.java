package com.backend.registration.service;

import com.backend.registration.dto.OrganizationDTO;
import com.backend.registration.dto.StudentDTO;
import com.backend.registration.entity.Organization;
import com.backend.registration.entity.Student;
import com.backend.registration.repository.OrganizationRepository;
import com.backend.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean register(OrganizationDTO dto) {
        if (organizationRepository.existsByEmail(dto.getEmail())) {
            return false;
        }

        Organization organization = new Organization();
        organization.setFullName(dto.getFullName());
        organization.setEmail(dto.getEmail());
        organization.setPassword(passwordEncoder.encode(dto.getPassword()));
        organization.setAccountType(dto.getAccountType());
        organization.setCountry(dto.getCountry());
        organization.setPhone(dto.getPhone());
        organization.setTimeZone(dto.getTimeZone());

        organizationRepository.save(organization);
        return true;
    }
}
