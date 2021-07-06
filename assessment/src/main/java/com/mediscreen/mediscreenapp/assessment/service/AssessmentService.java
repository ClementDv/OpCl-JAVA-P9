package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;

public interface AssessmentService {
    /**
     * Identify the RiskLevel of a patient
     *
     * @param patientId
     * @return AssessRiskResult with the patientId, the Factors who matched and the RiskLevel calculated
     */
    AssessRiskResult assessmentPatient(Long patientId);
}
