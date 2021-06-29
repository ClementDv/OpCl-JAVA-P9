package com.mediscreen.mediscreenapp.patient.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenapp.patient.data.PatientData;
import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.entity.Patient;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;
import com.mediscreen.mediscreenapp.patient.service.PatientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.InvalidParameterException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @MockBean
    private PatientService service;

    private final PatientController controller;

    private final MockMvc mvc;

    private final ObjectMapper jsonMapper;

    @Autowired
    public PatientControllerTest(PatientController controller, MockMvc mvc, ObjectMapper jsonMapper) {
        this.controller = controller;
        this.mvc = mvc;
        this.jsonMapper = jsonMapper;
    }

    @Test
    void getPatient() throws Exception {
        Mockito.when(service.getById(Mockito.anyLong())).thenAnswer(a -> {
            Long patientId = a.getArgument(0);
            if (patientId <= 12) {
                return PatientData.generatePatientDto(patientId);
            }
            throw new PatientNotFoundException();
        });

        // GetPatient
        long id = 12L;
        PatientDto patientDtoTest = PatientData.generatePatientDto(id);
        MvcResult result = mvc.perform(get("/patient/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        PatientDto patientDtoResult = jsonMapper.readValue(result.getResponse().getContentAsString(), PatientDto.class);
        Assertions.assertThat(patientDtoResult).isEqualTo(patientDtoTest);

        // PatientNotFound
        id = 22L;
        mvc.perform(get("/patient/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(r -> org.junit.jupiter.api.Assertions.assertTrue(
                        r.getResolvedException() instanceof PatientNotFoundException))
                .andReturn();
    }

    @Test
    void getAllPatients() throws Exception {
        List<PatientDto> patientDtoListTest = PatientData.generatePatientDtoList(10);
        Mockito.when(service.getAll()).thenReturn(patientDtoListTest);
        MvcResult result = mvc.perform(get("/patient/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<PatientDto> patientDtoListResult = jsonMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertThat(patientDtoListResult).isEqualTo(patientDtoListTest);
    }

    @Test
    void createPatient() throws Exception {
        PatientDto patientDto = PatientData.generatePatientDto(12L);
        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(patientDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        // PatientEmpty
        PatientDto emptyPatient = PatientDto.builder().build();
        Mockito.doThrow(InvalidParameterException.class).when(service).create(emptyPatient);
        mvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(emptyPatient))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(r -> org.junit.jupiter.api.Assertions.assertTrue(
                        r.getResolvedException() instanceof InvalidParameterException));
    }

    @Test
    void updatePatient() {
    }

    @Test
    void deletePatient() {
    }
}
