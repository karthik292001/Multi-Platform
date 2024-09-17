package com.example.MultiPlatform.services;



import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MultiPlatform.entities.Fields;
import com.example.MultiPlatform.entities.Stages;
import com.example.MultiPlatform.entities.Workflow;
import com.example.MultiPlatform.repositories.StageRepository;
import com.example.MultiPlatform.repositories.WorkflowRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private StageRepository stageRepository;

    private Random random = new Random();

    public Workflow saveWorkflow(Workflow workflow) {
        workflow.setStatus("PENDING");
        for (Stages stage : workflow.getStages()) {
            stage.setWorkflow(workflow);
            stage.setStatus("Pending");
            for (Fields field : stage.getFields()) {
                field.setStage(stage);
            }
        }

        return workflowRepository.save(workflow);
    }
    @Transactional
    public String handleWorkflowProgress(Long workflowId) {
        String msg="";

        Workflow workflow = workflowRepository.findById(workflowId).get();

        
        Stages currentStage = findCurrentStage(workflow);
        if (currentStage == null) {
            throw new IllegalStateException("No stage available to progress");
        }

        String status = currentStage.getStatus();
        if ("PENDING".equalsIgnoreCase(status)) {
            currentStage.setStatus("IN_PROGRESS");
            msg="Current stage is in progress";
            setRandomFieldValues(currentStage);
            //setFieldValues(nextStage,request.getFields());
        } else if ("IN_PROGRESS".equalsIgnoreCase(status)) {
            currentStage.setStatus("COMPLETED");
            Stages nextStage = findNextStage(workflow, currentStage);
            msg="Current stage is completed";
            if (nextStage == null) {
                workflow.setStatus("COMPLETED"); // Mark the workflow as completed if no next stage
            } else {
                nextStage.setStatus("IN_PROGRESS");
                //setFieldValues(nextStage,request.getFields());
                setRandomFieldValues(nextStage);
                stageRepository.save(nextStage);
            }
        } else {
            throw new IllegalStateException("Stage cannot be progressed from current state: " + status);
        }

        stageRepository.save(currentStage);
        workflowRepository.save(workflow);
        return msg;
    }

    private void setFieldValues(Stages stage,Map<String, String> fieldValues) {
        
        for (Fields field : stage.getFields()) {
            String value = fieldValues.get(field.getName());
            if (value != null) {
                field.setValue(value);
            }
        }
    }

    private void setRandomFieldValues(Stages stage) {
        for (Fields field : stage.getFields()) {
            String randomValue = generateRandomValueForField(field.getName());
            field.setValue(randomValue);
        }
    }

    private String generateRandomValueForField(String fieldName) {
        // Generate random values based on field name
        switch (fieldName) {
            case "Order Number":
                return "ORD" + random.nextInt(10000);
            case "Order Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Customer Name":
                return "Customer" + random.nextInt(100);
            case "Shipping Address":
                return "Address " + random.nextInt(100);
            case "Payment ID":
                return "PAY" + random.nextInt(10000);
            case "Payment Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Payment Amount":
                return "$" + (random.nextInt(1000) + 100);
            case "Payment Status":
                return random.nextBoolean() ? "SUCCESS" : "FAILED";
            case "Tracking Number":
                return "TRACK" + random.nextInt(10000);
            case "Shipping Carrier":
                return "Carrier" + random.nextInt(10);
            case "Estimated Delivery Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Delivery Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Delivery Confirmation":
                return random.nextBoolean() ? "CONFIRMED" : "NOT CONFIRMED";
            case "Completion Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Customer Feedback":
                return "Feedback " + random.nextInt(100);
            case "Application ID":
                return "APP" + random.nextInt(10000);
            case "Applicant Name":
                return "Applicant" + random.nextInt(100);
            case "Submission Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Interview Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Interview Time":
                return String.format("%02d:%02d", random.nextInt(24), random.nextInt(60)); // Random time
            case "Interviewer Name":
                return "Interviewer" + random.nextInt(100);
            case "Offer Letter Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Position":
                return "Position" + random.nextInt(10);
            case "Salary Offered":
                return "$" + (random.nextInt(50000) + 30000);
            case "Hire Date":
                return "2024-09-" + (random.nextInt(30) + 1); // Random date in September 2024
            case "Department":
                return "Department" + random.nextInt(10);
            case "Employee ID":
                return "EMP" + random.nextInt(10000);
            default:
                return "Unknown";
        }
    }

    private Stages findCurrentStage(Workflow workflow) {
        for (Stages stage : workflow.getStages()) {
            if (!"COMPLETED".equalsIgnoreCase(stage.getStatus())) {
                return stage;
            }
        }
        return null;
    }

    private Stages findNextStage(Workflow workflow, Stages currentStage) {
        boolean currentFound = false;
        for (Stages stage : workflow.getStages()) {
            if (currentFound) {
                return stage;
            }
            if (stage.equals(currentStage)) {
                currentFound = true;
            }
        }
        return null;
    }
}




