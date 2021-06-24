package com.mediscreen.mediscreenapp.assessment.service.impl;

import com.mediscreen.mediscreenapp.assessment.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;
import com.mediscreen.mediscreenapp.assessment.properties.NoteServiceProperties;
import com.mediscreen.mediscreenapp.assessment.service.RestNoteService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RestNoteServiceImpl implements RestNoteService {

    private final HttpHeaders headers;

    private final JSONObject jsonObject;

    private final NoteServiceProperties noteServiceProperties;

    private final RestTemplate restTemplate;

    @Autowired
    public RestNoteServiceImpl(HttpHeaders headers, JSONObject jsonObject, NoteServiceProperties noteServiceProperties, RestTemplate restTemplate) {
        this.headers = headers;
        this.jsonObject = jsonObject;
        this.noteServiceProperties = noteServiceProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Boolean> getSearchFactorsMap(Long patientId, List<String> factorsList) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(noteServiceProperties.getUrl())
                .path("/note/searchFactors");

        headers.setContentType(MediaType.APPLICATION_JSON);
        jsonObject.put("patientId", patientId)
                .put("factorsList", factorsList);
        HttpEntity<String> body = new HttpEntity<>(jsonObject.toString(), headers);

        try {
            ResponseEntity<SearchFactorsResult> response = restTemplate.postForEntity(builder.toUriString(), body, SearchFactorsResult.class);
            log.debug("Successfully get FactorsMap for the following patientId : {}", patientId);
            return Objects.requireNonNull(response.getBody()).getResult();
        } catch (HttpClientErrorException e) {
            throw new RestServiceTransmitException(e.getResponseBodyAsString());
        }
    }
}
