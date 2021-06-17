package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.dto.NoteDto;
import com.mediscreen.mediscreenapp.assessment.exception.RestPatientServiceException;
import com.mediscreen.mediscreenapp.assessment.properties.NoteServiceProperties;
import com.mediscreen.mediscreenapp.assessment.service.RestNoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Slf4j
public class RestNoteServiceImpl implements RestNoteService {

    private final NoteServiceProperties noteServiceProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public RestNoteServiceImpl(NoteServiceProperties noteServiceProperties, RestTemplate restTemplate) {
        this.noteServiceProperties = noteServiceProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<NoteDto> getNotesFromPatientId(Long patientId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(noteServiceProperties.getUrl())
                .path("/note/{id}")
                .uriVariables(Map.of("id", patientId));

        try {
            ResponseEntity<NoteDto[]> response = restTemplate.getForEntity(
                    builder.toUriString(),
                    NoteDto[].class
            );

            if (ArrayUtils.isEmpty(response.getBody())) {
                log.debug("No note for Patient with the following id : {}", patientId);
                return Collections.emptyList();
            }
            log.debug("Successfully get Note(s) for the following patientId : {}", patientId);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        }
        catch (HttpClientErrorException e) {
            throw new RestPatientServiceException(e.getResponseBodyAsString());
        }
    }
}
