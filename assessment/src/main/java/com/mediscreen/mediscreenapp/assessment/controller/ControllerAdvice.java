package com.mediscreen.mediscreenapp.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenapp.assessment.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestServiceTransmitException.class)
    @ResponseBody
    public ResponseEntity<?> handlePatientNotFoundException(RestServiceTransmitException e) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = mapper.readValue(e.getErrorResponse(), ErrorResponse.class);
        return response(errorResponse);
    }

    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.valueOf(errorResponse.getStatus());
        log.error(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
