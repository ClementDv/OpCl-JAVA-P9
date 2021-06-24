package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;

public interface AssessmentService {
    AssessRiskResult assessmentPatient(Long patientId);
}
