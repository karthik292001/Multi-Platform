package com.example.MultiPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MultiPlatform.entities.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
}