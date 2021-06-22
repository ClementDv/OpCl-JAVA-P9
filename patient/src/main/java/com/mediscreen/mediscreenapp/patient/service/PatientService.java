package com.mediscreen.mediscreenapp.patient.service;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;

import java.util.List;

public interface PatientService {

    PatientDto getById(Long id);

    List<PatientDto> getAll();

    void create(PatientDto patientDto);

    void update(PatientDto patientDto);

    void delete(Long id);
}
