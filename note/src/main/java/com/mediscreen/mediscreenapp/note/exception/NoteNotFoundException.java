package com.mediscreen.mediscreenapp.note.exception;

import lombok.Getter;

@Getter
public class NoteNotFoundException extends RuntimeException {

    private Long id;

    public NoteNotFoundException() {
    }

    public NoteNotFoundException(Long id) {
        super("Note not Found");
        this.id = id;
    }

    public NoteNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
