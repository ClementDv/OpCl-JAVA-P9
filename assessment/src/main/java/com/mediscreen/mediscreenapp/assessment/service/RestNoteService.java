package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.dto.NoteDto;

import java.util.List;

public interface RestNoteService {
    List<NoteDto> getNotesFromPatientId(Long patientId);
}
