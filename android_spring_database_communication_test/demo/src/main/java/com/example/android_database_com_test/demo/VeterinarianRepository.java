package com.example.android_database_com_test.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer>{
    Optional<Veterinarian> findByName(String name);
}
