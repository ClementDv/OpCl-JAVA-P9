package com.mediscreen.mediscreenapp.assessment.data;

import com.mediscreen.mediscreenapp.assessment.domain.Factors;
import com.mediscreen.mediscreenapp.assessment.domain.RiskLevel;
import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AssessmentData {

    private static Random random = new Random(10L);

    public static Map<String, Boolean> getMapFactors(List<String> factorsList) {
        Map<String, Boolean> factorsResultMap = new HashMap<>();
        for (String factor : factorsList) {
            factorsResultMap.put(factor, random.nextBoolean());
        }
        return factorsResultMap;
    }

    public static Map<String, Boolean> getMapFactorsWithNumberRisk(int numberRisk) {
        Map<String, Boolean> factorsResultMap = new HashMap<>();
        int i = 0;
        for (Factors factors : Factors.values()) {
            for (String term : factors.getTerms()) {
                if (i < numberRisk) {
                    factorsResultMap.put(term, Boolean.TRUE);
                } else {
                    factorsResultMap.put(term, Boolean.FALSE);
                }
                i++;
                break;
            }
        }
        return factorsResultMap;
    }

    public static List<String> getFactorsListFromTermList(Map<String, Boolean> termList) {
        List<String> factorsList = new ArrayList<>();
        for (String term : termList.keySet()) {
            factorLoop:
            for (Factors factors : Factors.values()) {
                for (String termEnum : factors.getTerms()) {
                    if (term.equals(termEnum) && termList.get(term)) {
                        factorsList.add(factors.toString());
                        break factorLoop;
                    }
                }
            }
        }
        return factorsList.stream().sorted().collect(Collectors.toList());
    }

    public static PatientDto generatePatientDto(Long id) {
        return PatientDto.builder()
                .id(id)
                .firstName("Test" + id)
                .lastName("TestName")
                .gender("male")
                .address("TestAdress")
                .birthDate(LocalDate.now())
                .phoneNumber("TestPhoneNumber")
                .build();
    }

    public static PatientDto generatePatientDtoWithAgeAndGender(Long id, int age, String gender) {
        return PatientDto.builder()
                .id(id)
                .firstName("Test" + id)
                .lastName("TestName")
                .gender(gender)
                .address("TestAdress")
                .birthDate(getBirthDateFromAge(age))
                .phoneNumber("TestPhoneNumber")
                .build();
    }

    private static LocalDate getBirthDateFromAge(int age) {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear() - age, now.getMonth(), now.getDayOfMonth());
    }

    public static AssessRiskResult getRandomAssessRiskResult(long patientId) {
        return AssessRiskResult
                .builder()
                .patientId(patientId)
                .riskLevel(RiskLevel.values()[random.nextInt(RiskLevel.values().length)])
                .factorsTermsMatch(Factors.getAllFactors())
                .build();
    }
}
