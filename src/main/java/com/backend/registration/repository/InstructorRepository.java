package com.backend.registration.repository;

import com.backend.registration.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByEmail(String email);
    Optional<Instructor> findByEmail(String email);
}
