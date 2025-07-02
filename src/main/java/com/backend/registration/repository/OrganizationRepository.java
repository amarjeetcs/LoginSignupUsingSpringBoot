package com.backend.registration.repository;

import com.backend.registration.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByEmail(String email);
    Optional<Organization> findByEmail(String email);

}
