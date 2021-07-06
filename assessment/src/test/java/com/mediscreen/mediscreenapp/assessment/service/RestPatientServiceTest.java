package com.mediscreen.mediscreenapp.assessment.service;

import com.mediscreen.mediscreenapp.assessment.data.AssessmentData;
import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
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

@SpringBootTest
class RestPatientServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    private final RestPatientService service;

    @Autowired
    RestPatientServiceTest(RestPatientService service) {
        this.service = service;
    }

    @Test
    void getPatient() {
        Long patientId = 5L;
        PatientDto  patientDto = AssessmentData.generatePatientDto(patientId);

        // GetPatientDTO
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(patientDto, HttpStatus.OK));
        Assertions.assertThat(service.getPatient(patientId)).isEqualTo(patientDto);

        // ThrowException
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.valueOf(412), ""));
        Assertions.assertThatThrownBy(() -> service.getPatient(patientId)).isInstanceOf(RestServiceTransmitException.class);
    }
}
