package com.mediscreen.mediscreenapp.assessment.exception;

import lombok.Getter;

@Getter
public class RestServiceTransmitException extends RuntimeException {

    private String errorResponse;

    public RestServiceTransmitException() {
    }

    public RestServiceTransmitException(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public RestServiceTransmitException(String message, String errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
