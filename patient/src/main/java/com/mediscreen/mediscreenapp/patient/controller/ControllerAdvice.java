package com.mediscreen.mediscreenapp.patient.controller;

import com.mediscreen.mediscreenapp.patient.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.patient.exception.ErrorCodesEnum;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidParameterException;
import java.util.HashMap;

import static com.mediscreen.mediscreenapp.patient.exception.ErrorCodesEnum.INVALID_PARAMETER;
import static com.mediscreen.mediscreenapp.patient.exception.ErrorCodesEnum.PATIENT_NOT_FOUND;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handlePatientNotFoundException(PatientNotFoundException e) {
        return response(ErrorResponse
                .builder()
                .status(PATIENT_NOT_FOUND.getStatus())
                .code(PATIENT_NOT_FOUND.getCode())
                .message(e.getMessage())
                .metadata(new HashMap<>())
                .build()
                .withMetadata("id", e.getPatientId()));
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException e) {
        return response(ErrorResponse
                .builder()
                .status(INVALID_PARAMETER.getStatus())
                .code(INVALID_PARAMETER.getCode())
                .message(e.getMessage())
                .metadata(new HashMap<>())
                .build());
    }

    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.valueOf(errorResponse.getStatus());
        log.error(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
