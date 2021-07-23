package com.mediscreen.mediscreenapp.note.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreenapp.note.data.NoteData;
import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.entity.Note;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @MockBean
    private NoteService service;

    private final MockMvc mvc;

    private final ObjectMapper jsonMapper;

    @Autowired
    NoteControllerTest(MockMvc mvc, ObjectMapper jsonMapper) {
        this.mvc = mvc;
        this.jsonMapper = jsonMapper;
    }

    @BeforeEach
    void setUp() {
        jsonMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE); // set jackson deserialization without "[UTC]"
    }

    @Test
    void getNoteById() throws Exception {
        long id = 5L;
        NoteDto note = NoteData.generateNoteDto(id);
        Mockito.when(service.getNote(id)).thenReturn(note);

        MvcResult result = mvc.perform(get("/note/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        NoteDto noteDtoResult = jsonMapper.readValue(result.getResponse().getContentAsString(), NoteDto.class);
        Assertions.assertThat(noteDtoResult).isEqualTo(note);
    }

    @Test
    void getNotesFromPatientId() throws Exception {
        long id = 12L;
        List<NoteDto> noteDtoList = NoteData.generateNoteDtoList(10, id);
        Mockito.when(service.getByPatientId(Mockito.anyLong())).thenAnswer(a -> {
            Long argument = a.getArgument(0);
            System.out.println(argument);
            if (argument <= 0) {
                throw new InvalidParameterException();
            }
            return noteDtoList;
        });
        // GetNotes
        MvcResult result = mvc.perform(get("/note")
                .param("patientId", Long.toString(id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        List<NoteDto> noteDtoListResult = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(noteDtoListResult).isEqualTo(noteDtoList);
        // InvalidParam
        mvc.perform(get("/note")
                .param("patientId", "-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void createNote() throws Exception {
        NoteDto noteDto = NoteData.generateNoteDto(1L);
        // Create
        mvc.perform(post("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(noteDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // InvalidParam
        Mockito.doThrow(InvalidParameterException.class).when(service).create(noteDto);
        mvc.perform(post("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(noteDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void updateNote() throws Exception {
        NoteDto noteDto = NoteData.generateNoteDto(1L);

        // Update
        mvc.perform(put("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(noteDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // NoteNotFound
        Mockito.doThrow(NoteNotFoundException.class).when(service).update(noteDto);
        mvc.perform(put("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(noteDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        // InvalidParam
        Mockito.doThrow(InvalidParameterException.class).when(service).update(noteDto);
        mvc.perform(put("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(noteDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void deleteNote() throws Exception {
        long id = 22L;
        mvc.perform(delete("/note/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void searchFactors() throws Exception {
        SearchFactorsRequest searchFactorsRequest = SearchFactorsRequest.builder()
                .patientId(1L)
                .factorsTermList(List.of("test", "test1", "test2"))
                .build();

        Map<String, Boolean> factorsMapTest = Map.of("test", true, "test1", true, "test2", false);
        SearchFactorsResult searchFactorsResult = SearchFactorsResult.builder()
                .result(factorsMapTest)
                .build();

        Mockito.when(service.searchTermsFactors(Mockito.eq(searchFactorsRequest))).thenReturn(searchFactorsResult);
        MvcResult result = mvc.perform(post("/note/searchTermsFactors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(searchFactorsRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        SearchFactorsResult searchFactorsResultRead = jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertThat(searchFactorsResultRead).isEqualTo(searchFactorsResult);
    }
}
