package com.mediscreen.mediscreenapp.note.service;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> getNotesFromPatientId(Long patientId);

    void addNote(NoteDto noteDto);

    void updateNote(NoteDto noteDto);

    void deleteNote(Long id);
}
