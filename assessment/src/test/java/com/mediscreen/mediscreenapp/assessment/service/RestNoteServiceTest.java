package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.data.AssessmentData;
import com.mediscreen.mediscreenapp.assessment.domain.Factors;
import com.mediscreen.mediscreenapp.assessment.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class RestNoteServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    private final RestNoteService service;

    @Autowired
    RestNoteServiceTest(RestNoteService service) {
        this.service = service;
    }

    @Test
    void getSearchTermsFactorsMap() {
        long patientId =  1L;
        List<String> factorsList = Factors.getAllFactors();

        // GetMap
        Map<String, Boolean> factorsMap = AssessmentData.getMapFactors(factorsList);
        SearchFactorsResult searchFactorsResult = SearchFactorsResult
                .builder()
                .result(factorsMap)
                .build();
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(searchFactorsResult, HttpStatus.OK));
        Assertions.assertThat(service.getSearchTermsFactorsMap(patientId, factorsList)).isEqualTo(factorsMap);

        // ThrowException
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.valueOf(412), ""));
        Assertions.assertThatThrownBy(() -> service.getSearchTermsFactorsMap(patientId, factorsList)).isInstanceOf(RestServiceTransmitException.class);
    }
}
