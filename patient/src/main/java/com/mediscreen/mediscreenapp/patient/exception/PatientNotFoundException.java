package com.mediscreen.mediscreenapp.patient.exception;

import lombok.Getter;

@Getter
public class PatientNotFoundException extends RuntimeException {

    private Long patientId;

    public PatientNotFoundException() {
        super();
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(Long id) {
        super("Patient not found with the following id : " + id);
        this.patientId = id;
    }

    public PatientNotFoundException(String message, Long id) {
        super(message);
        this.patientId = id;
    }
}
