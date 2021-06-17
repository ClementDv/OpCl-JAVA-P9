package com.mediscreen.mediscreenapp.assessment.exception;

import lombok.Getter;

@Getter
public class RestPatientServiceException extends RuntimeException {

    private String errorResponse;

    public RestPatientServiceException() {
    }

    public RestPatientServiceException(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public RestPatientServiceException(String message, String errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
