package com.mediscreen.mediscreenapp.patient.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodesEnum {

    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "PATIENT_NOT_FOUND");

    private final int status;
    private final String code;
}
