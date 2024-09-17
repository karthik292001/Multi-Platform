package com.example.MultiPlatform.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MultiPlatform.entities.Workflow;
import com.example.MultiPlatform.services.WorkflowService;





@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping
    public Workflow createWorkflow(@RequestBody Workflow workflow) {
        return workflowService.saveWorkflow(workflow);
    }
    @PostMapping("/{workflowId}/progress")
    public String handleWorkflowProgress(@PathVariable Long workflowId) {
        return workflowService.handleWorkflowProgress(workflowId);
    }
}


