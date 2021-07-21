package com.mediscreen.mediscreenapp.note.service;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;

import java.security.InvalidParameterException;
import java.util.List;

public interface NoteService {

    /**
     * Get a note corresponding to its Id;
     *
     * @param noteId to identify te note
     * @return the noteDTO corresponding to the noteId
     * @throws InvalidParameterException if the noteId is negative or null
     * @throws NoteNotFoundException if there's no not found
     */
    NoteDto getNote(Long noteId);

    /**
     * Get all notes from a patient
     *
     * @param patientId to identify notes
     * @return a List of noteDto
     * @throws InvalidParameterException if the patientId is negative or null
     */
    List<NoteDto> getByPatientId(Long patientId);

    /**
     * Add a note to the DataBase
     *
     * @param noteDto the note to add
     * @throws InvalidParameterException if the note is null or empty
     */
    void create(NoteDto noteDto);

    /**
     * Update a note from the DataBase
     *
     * @param noteDto use the id to find the note to update with the content of this param
     * @throws InvalidParameterException if the noteDto is empty or if the noteId is null or negative
     * @throws NoteNotFoundException if the note doesn't exist in the DataBase
     */
    void update(NoteDto noteDto);

    /**
     * Delete a note from the DataBase
     *
     * @param id of the note to delete
     * @throws InvalidParameterException if the id is null or negative
     * @throws NoteNotFoundException if the note doesn't exist in the DataBase
     */
    void delete(Long id);

    /**
     * Send a request with the patientId to find his notes and the a list of terms to find.
     *
     * @param request contains patiendId and list of terms
     * @return SearchFactorsResult with the map of the terms found
     * Map<String, Boolean> with the term name and the value true if it's present and false if not.
     * @throws InvalidParameterException if the request is empty or null
     * @throws NoteNotFoundException if there's no notes for the patientId
     */
    SearchFactorsResult searchTermsFactors(SearchFactorsRequest request);
}
