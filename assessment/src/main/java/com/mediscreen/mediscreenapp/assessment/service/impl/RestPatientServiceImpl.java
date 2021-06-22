package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.dto.PatientDto;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;
import com.mediscreen.mediscreenapp.assessment.properties.PatientServiceProperties;
import com.mediscreen.mediscreenapp.assessment.service.RestPatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@Slf4j
public class RestPatientServiceImpl implements RestPatientService {

    private final PatientServiceProperties patientServiceProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public RestPatientServiceImpl(PatientServiceProperties patientServiceProperties, RestTemplate restTemplate) {
        this.patientServiceProperties = patientServiceProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public PatientDto getPatient(Long patientId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(patientServiceProperties.getUrl())
                .path("/patient/{id}")
                .uriVariables(Map.of("id", patientId));

        try {
            ResponseEntity<PatientDto> response = restTemplate.getForEntity(
                    builder.toUriString(),
                    PatientDto.class
            );
            log.debug("Successfully get Patient for the following id : {}", patientId);
            return response.getBody();
        }
        catch (HttpClientErrorException e) {
            throw new RestServiceTransmitException(e.getResponseBodyAsString());
        }
    }

}
