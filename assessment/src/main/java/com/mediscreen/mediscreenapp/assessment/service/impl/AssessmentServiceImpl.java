package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.domain.Factors;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import com.mediscreen.mediscreenapp.assessment.service.RestNoteService;
import com.mediscreen.mediscreenapp.assessment.service.RestPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        List<String> factorsList = getFactorsList();
        Map<String, Boolean> searchFactorsMap = restNoteService.getSearchFactorsMap(patientDto.getId(), factorsList);
        long factorsNb = calculateFactorsFromFactorsMap(searchFactorsMap);
        System.out.println("factorsNB : " + factorsNb);

    }

    private long calculateFactorsFromFactorsMap(Map<String, Boolean> searchFactorsMap) {
        if (!CollectionUtils.isEmpty(searchFactorsMap)) {
            return searchFactorsMap.values().stream().filter(e -> e).count();
        }
        return -1;
    }

    private List<String> getFactorsList() {
        List<String> factorList = new ArrayList<>();
        Arrays.stream(Factors.values()).forEach(e -> factorList.addAll(Arrays.asList(e.getTerms())));
        return factorList;
    }

}
