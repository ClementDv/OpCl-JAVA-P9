package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.domain.Factors;
import com.mediscreen.mediscreenapp.assessment.domain.RiskLevel;
import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import com.mediscreen.mediscreenapp.assessment.service.RestNoteService;
import com.mediscreen.mediscreenapp.assessment.service.RestPatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AssessmentServiceImpl implements AssessmentService {


    private final RestPatientService restPatientService;

    private final RestNoteService restNoteService;

    @Autowired
    public AssessmentServiceImpl(RestPatientService restPatientService, RestNoteService restNoteService) {
        this.restPatientService = restPatientService;
        this.restNoteService = restNoteService;
    }

    @Override
    public AssessRiskResult assessmentPatient(Long patientId) {
        PatientDto patient = restPatientService.getPatient(patientId);
        Map<String, Boolean> searchFactorsMap = restNoteService.getSearchFactorsMap(patient.getId(), Factors.getAllFactors());
        List<String> factorsMatchList = getOnlyUniqueFactorsMatchList(searchFactorsMap);
        long factorsNb = factorsMatchList.size();

        AssessRiskResult result = AssessRiskResult.builder()
                .patientId(patientId)
                .factorsTermsMatch(factorsMatchList)
                .riskLevel(getRiskLevel(patient, factorsNb))
                .build();
        log.info("Assessment successful for following patient id : {}", patientId);
        return result;
    }

    private List<String> getOnlyUniqueFactorsMatchList(Map<String, Boolean> searchFactorsMap) {
        List<String> searchUniqueFactorsMatchList = new ArrayList<>();
        for (Factors factors : Factors.values()) {
            for (String term : factors.getTerms()) {
                if (Boolean.TRUE.equals(searchFactorsMap.get(term))) {
                    searchUniqueFactorsMatchList.add(factors.toString());
                    break;
                }
            }
        }
        return searchUniqueFactorsMatchList.stream().sorted().collect(Collectors.toList());
    }

    private RiskLevel getRiskLevel(PatientDto patient, long factorsNb) {
        int age = Period.between(patient.getBirthDate(), LocalDate.now(ZoneOffset.UTC)).getYears();
        if (age < 30) {
            boolean male = patient.getGender().contentEquals("male");
            if (factorsNb >= (male ? 5 : 7)) {
                return RiskLevel.EARLY_ONSET;
            }
            if (factorsNb >= (male ? 3 : 4)) {
                return RiskLevel.IN_DANGER;
            }
        } else {
            if (factorsNb >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
            if (factorsNb >= 6) {
                return RiskLevel.IN_DANGER;
            }
            if (factorsNb >= 2) {
                return RiskLevel.BORDERLINE;
            }
        }
        return RiskLevel.NONE;
    }

}
