package com.mediscreen.mediscreenapp.assessment.service;

import java.util.List;
import java.util.Map;

public interface RestNoteService {
    Map<String, Boolean> getSearchFactorsMap(Long patientId, List<String> factorsList);
}
