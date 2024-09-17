package com.example.MultiPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MultiPlatform.entities.Fields;

public interface FieldRepository extends JpaRepository<Fields, Long> {
}
