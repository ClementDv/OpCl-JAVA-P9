package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.data.AssessmentData;
import com.mediscreen.mediscreenapp.assessment.domain.Factors;
import com.mediscreen.mediscreenapp.assessment.domain.RiskLevel;
import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class AssessmentServiceTest {

    @MockBean
    private RestPatientService restPatientService;

    @MockBean
    private RestNoteService restNoteService;

    private final AssessmentService service;

    @Autowired
    AssessmentServiceTest(AssessmentService service) {
        this.service = service;
    }

    @Test
    void assessmentPatientMaleLess30y() {
        //  RiskLevel : Early onset
        assertAssessment(25, "male", 6, RiskLevel.EARLY_ONSET);
        assertAssessment(25, "male", 5, RiskLevel.EARLY_ONSET);

        //  RiskLevel : In Danger
        assertAssessment(25, "male", 3, RiskLevel.IN_DANGER);
        assertAssessment(25, "male", 4, RiskLevel.IN_DANGER);

        //  RiskLevel : None
        assertAssessment(25, "male", 2, RiskLevel.NONE);
        assertAssessment(25, "male", 1, RiskLevel.NONE);
        assertAssessment(25, "male", 0, RiskLevel.NONE);
    }

    @Test
    void assessmentPatientMaleMore30y() {
        //  RiskLevel : Early onset
        assertAssessment(35, "male", 9, RiskLevel.EARLY_ONSET);
        assertAssessment(35, "male", 8, RiskLevel.EARLY_ONSET);

        //  RiskLevel : In Danger
        assertAssessment(35, "male", 7, RiskLevel.IN_DANGER);
        assertAssessment(35, "male", 6, RiskLevel.IN_DANGER);

        //  RiskLevel : Borderline
        assertAssessment(35, "male", 5, RiskLevel.BORDERLINE);
        assertAssessment(35, "male", 4, RiskLevel.BORDERLINE);
        assertAssessment(35, "male", 3, RiskLevel.BORDERLINE);
        assertAssessment(35, "male", 2, RiskLevel.BORDERLINE);

        //  RiskLevel : None
        assertAssessment(35, "male", 1, RiskLevel.NONE);
        assertAssessment(35, "male", 0, RiskLevel.NONE);
    }

    @Test
    void assessmentPatientFemaleLess30y() {
        //  RiskLevel : Early onset
        assertAssessment(25, "female", 8, RiskLevel.EARLY_ONSET);
        assertAssessment(25, "female", 7, RiskLevel.EARLY_ONSET);

        //  RiskLevel : In Danger
        assertAssessment(25, "female", 6, RiskLevel.IN_DANGER);
        assertAssessment(25, "female", 5, RiskLevel.IN_DANGER);
        assertAssessment(25, "female", 4, RiskLevel.IN_DANGER);

        //  RiskLevel : None
        assertAssessment(25, "female", 3, RiskLevel.NONE);
        assertAssessment(25, "female", 1, RiskLevel.NONE);
        assertAssessment(25, "female", 2, RiskLevel.NONE);
        assertAssessment(25, "female", 0, RiskLevel.NONE);
    }

    @Test
    void assessmentPatientFemaleMore30y() {
        //  RiskLevel : Early onset
        assertAssessment(35, "female", 9, RiskLevel.EARLY_ONSET);
        assertAssessment(35, "female", 8, RiskLevel.EARLY_ONSET);

        //  RiskLevel : In Danger
        assertAssessment(35, "female", 7, RiskLevel.IN_DANGER);
        assertAssessment(35, "female", 6, RiskLevel.IN_DANGER);

        //  RiskLevel : Borderline
        assertAssessment(35, "female", 5, RiskLevel.BORDERLINE);
        assertAssessment(35, "female", 4, RiskLevel.BORDERLINE);
        assertAssessment(35, "female", 3, RiskLevel.BORDERLINE);
        assertAssessment(35, "female", 2, RiskLevel.BORDERLINE);

        //  RiskLevel : None
        assertAssessment(35, "female", 1, RiskLevel.NONE);
        assertAssessment(35, "female", 0, RiskLevel.NONE);
    }

    private void assertAssessment(int age, String gender, int factorsNumber, RiskLevel expectedRiskLevel) {
        Long patientId = 5L;
        PatientDto patientDto = AssessmentData.generatePatientDtoWithAgeAndGender(patientId, age, gender);
        List<String> factorsList = Factors.getAllFactors();
        Map<String, Boolean> mapFactors = AssessmentData.getMapFactorsWithNumberRisk(factorsNumber);

        Mockito.when(restPatientService.getPatient(patientId)).thenReturn(patientDto);
        Mockito.when(restNoteService.getSearchFactorsMap(Mockito.eq(patientId), Mockito.eq(factorsList)))
                .thenReturn(mapFactors);
        AssessRiskResult assessRiskResult = AssessRiskResult.builder()
                .patientId(patientId)
                .factorsTermsMatch(AssessmentData.getFactorsListFromTermList(mapFactors))
                .riskLevel(expectedRiskLevel)
                .build();
        Assertions.assertThat(service.assessmentPatient(patientId)).isEqualTo(assessRiskResult);
    }
}
