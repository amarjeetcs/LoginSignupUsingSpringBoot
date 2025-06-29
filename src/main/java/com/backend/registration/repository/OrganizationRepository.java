package com.backend.registration.repository;

import com.backend.registration.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByEmail(String email);
}
