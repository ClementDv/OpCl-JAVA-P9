package com.mediscreen.mediscreenapp.note.controller;


import com.mediscreen.mediscreenapp.note.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static com.mediscreen.mediscreenapp.note.exception.ErrorCodesEnum.NOTE_NOT_FOUND;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handlePatientNotFoundException(NoteNotFoundException e) {
        return response(ErrorResponse
                .builder()
                .status(NOTE_NOT_FOUND.getStatus())
                .code(NOTE_NOT_FOUND.getCode())
                .message(e.getMessage())
                .metadata(new HashMap<>())
                .build()
                .withMetadata("id", e.getId())
        );
    }

    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status = HttpStatus.valueOf(errorResponse.getStatus());
        log.error(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
