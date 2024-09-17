package com.example.MultiPlatform.entities;

import java.util.Map;

import lombok.Data;

@Data
public class WorkflowProgressRequest {
    private Map<String, String> fields;
}
