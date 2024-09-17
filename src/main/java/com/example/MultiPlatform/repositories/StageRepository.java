package com.example.MultiPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MultiPlatform.entities.Stages;


public interface StageRepository extends JpaRepository<Stages, Long> {
}
