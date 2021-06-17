package com.mediscreen.mediscreenapp.patient.service;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto getPatient(Long id);

    List<PatientDto> getAllPatients();

    void addPatient(PatientDto patientDto);

    void updatePatient(PatientDto patientDto);

    void deletePatient(Long id);
}
