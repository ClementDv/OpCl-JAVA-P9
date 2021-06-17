package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.dto.NoteDto;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import com.mediscreen.mediscreenapp.assessment.service.RestNoteService;
import com.mediscreen.mediscreenapp.assessment.service.RestPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final RestPatientService restPatientService;

    private final RestNoteService restNoteService;

    @Autowired
    public AssessmentServiceImpl(RestPatientService restPatientService, RestNoteService restNoteService) {
        this.restPatientService = restPatientService;
        this.restNoteService = restNoteService;
    }

    @Override
    public void assessmentPatient(Long patientId) {
        PatientDto patientDto = restPatientService.getPatient(patientId);
        System.out.println("patient : " + patientDto);
        List<NoteDto> noteDtoList = restNoteService.getNotesFromPatientId(patientDto.getId());
        System.out.println("notes : " + noteDtoList);
    }
}
