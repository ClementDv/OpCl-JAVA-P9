package com.mediscreen.mediscreenapp.patient.service;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;

import java.security.InvalidParameterException;
import java.util.List;

public interface PatientService {
    /**
     * Get a patientDto
     *
     * @param id the patientId
     * @return a Dto corresponding of the id
     * @throws PatientNotFoundException if the patient doesn't exist.
     */
    PatientDto getById(Long id);

    /**
     * Get all patientDto as list
     *
     * @return a list of Dto of each patient
     */
    List<PatientDto> getAll();

    /**
     * Add a patient to the DataBase
     *
     * @param patientDto the patient to add
     * @throws InvalidParameterException if the patientDto param is null or em pty
     */
    void create(PatientDto patientDto);

    /**
     * Update a patient from the DataBase
     *
     * @param patientDto use the id to find the patient to update with the content of this param
     * @throws PatientNotFoundException if the patient to update doesn't exist
     * @throws InvalidParameterException if the patientDto param is null or em pty
     */
    void update(PatientDto patientDto);

    /**
     * Delete a patient from the DataBase
     *
     * @param id the patientId
     * @throws PatientNotFoundException if the patient to delete doesn't exist
     */
    void delete(Long id);
}
