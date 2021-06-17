package com.mediscreen.mediscreenapp.assessment.controller;

import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/assess/{id}")
    public void assessmentPatient(@PathVariable("id") Long patientId) {
        assessmentService.assessmentPatient(patientId);
    }
}
