package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;

public interface RestPatientService {
    /**
     * Http get request to the NoteController in Note module to the following path : "/patient/{patientId}"
     *
     * @param patientId to identify patient
     * @return patientDto from the patientId send
     * @throws RestServiceTransmitException to throw the exception catch from the Controller called
     */
    PatientDto getPatient(Long patientId);
}
