package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;

public interface RestPatientService {

    PatientDto getPatient(Long patientId);
}
