package com.mediscreen.mediscreenapp.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenapp.assessment.data.AssessmentData;
import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;
import com.mediscreen.mediscreenapp.assessment.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.assessment.exception.RestServiceTransmitException;
import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import org.assertj.core.api.Assertions;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AssessmentController.class)
class AssessmentControllerTest {

    @MockBean
    private AssessmentService service;

    private final MockMvc mvc;

    private final ObjectMapper jsonMapper;

    @Autowired
    AssessmentControllerTest(MockMvc mvc, ObjectMapper jsonMapper) {
        this.mvc = mvc;
        this.jsonMapper = jsonMapper;
    }

    @Test
    void assessmentPatient() throws Exception {
        // Assert assessRiskResult
        long patientId = 12L;
        AssessRiskResult assessRiskResult = AssessmentData.getRandomAssessRiskResult(patientId);

        Mockito.when(service.assessmentPatient(patientId)).thenReturn(assessRiskResult);
        MvcResult result = mvc.perform(get("/assess/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AssessRiskResult assessRiskResultFromResult = jsonMapper.readValue(result.getResponse().getContentAsString(), AssessRiskResult.class);
        Assertions.assertThat(assessRiskResultFromResult).isEqualTo(assessRiskResult);

        // Throw RestException : RestException
        ErrorResponse NotesNotFound = ErrorResponse
                .builder()
                .status(404)
                .code("NOT_FOUND")
                .message("not found")
                .metadata(new HashMap<>())
                .build();
        String errorResponse = jsonMapper.writeValueAsString(NotesNotFound);
        Mockito.when(service.assessmentPatient(patientId)).thenThrow(new RestServiceTransmitException(errorResponse));
        mvc.perform(get("/assess/" + patientId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
