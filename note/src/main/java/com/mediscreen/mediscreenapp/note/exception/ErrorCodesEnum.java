package com.mediscreen.mediscreenapp.note.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodesEnum {

    INVALID_PARAMETER(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_PARAMETER"),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "NOTE_NOT_FOUND");

    private final int status;
    private final String code;
}
