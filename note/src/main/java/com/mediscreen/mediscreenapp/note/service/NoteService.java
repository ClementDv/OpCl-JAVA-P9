package com.mediscreen.mediscreenapp.note.service;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> getByPatientId(Long patientId);

    void create(NoteDto noteDto);

    void update(NoteDto noteDto);

    void delete(Long id);
}
