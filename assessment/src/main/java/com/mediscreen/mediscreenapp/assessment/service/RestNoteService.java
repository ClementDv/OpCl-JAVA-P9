package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;

import java.util.List;
import java.util.Map;

public interface RestNoteService {
    /**
     * Http post request to the NoteController in Note module to the following path : "/note/searchTermsFactors"
     *
     * @param patientId to get all patient's notes
     * @param termsList all terms to find
     * @return the map with the term and if there are present or not in the patient
     * @throws RestServiceTransmitException to throw the exception catch from the Controller called
     */
    Map<String, Boolean> getSearchTermsFactorsMap(Long patientId, List<String> termsList);
}
