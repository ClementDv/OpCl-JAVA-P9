package com.mediscreen.mediscreenapp.patient.service;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto getAPatient(Long id);

    List<PatientDto> getAllPatients();

    void addAPatient(PatientDto patientDto);

    void updatePatient(PatientDto patientDto, Long id);

    void deletePatient(Long id);
}
