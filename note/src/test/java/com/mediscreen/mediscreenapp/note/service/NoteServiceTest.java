package com.mediscreen.mediscreenapp.note.service;

import com.mediscreen.mediscreenapp.note.data.NoteData;
import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.entity.Note;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;
import com.mediscreen.mediscreenapp.note.mapper.NoteMapper;
import com.mediscreen.mediscreenapp.note.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class NoteServiceTest {

    @MockBean
    private NoteRepository repository;

    @MockBean
    private TimeService timeService;

    @MockBean
    private SequenceServiceGenerator sequenceServiceGenerator;

    private final NoteService noteService;

    private final NoteMapper noteMapper;

    @Autowired
    NoteServiceTest(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @Test
    void getNote() {
        long id = 22L;
        // Get note
        Note note = NoteData.generateNote(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(note));
        Assertions.assertThat(noteService.getNote(id)).isEqualTo(noteMapper.toDto(note));

        // InvalidParameterException
        Assertions.assertThatThrownBy(() -> noteService.getNote(-22L)).isInstanceOf(InvalidParameterException.class);
        // NoteNotFoundException
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> noteService.getNote(id)).isInstanceOf(NoteNotFoundException.class);
    }

    @Test
    void getByPatientId() {
        long id = 1L;
        List<Note> noteList = NoteData.generateNoteList(5, id);
        Mockito.when(repository.getAllByPatientId(id)).thenReturn(noteList);
        // NotesFound
        List<NoteDto> noteDtoList = noteList.stream().map(noteMapper::toDto).collect(Collectors.toList());
        Assertions.assertThat(noteService.getByPatientId(id)).isEqualTo(noteDtoList);
        // NotesNotFound
        Mockito.when(repository.getAllByPatientId(id)).thenReturn(List.of());
        Assertions.assertThat(noteService.getByPatientId(id)).isEqualTo(List.of());
        // InvalidParam
        Assertions.assertThatThrownBy(() -> noteService.getByPatientId(null)).isInstanceOf(InvalidParameterException.class);
        Assertions.assertThatThrownBy(() -> noteService.getByPatientId(-1L)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void create() {
        long patientId = 5L;
        NoteDto noteDto = NoteData.generateNoteDto(patientId);
        Mockito.when(sequenceServiceGenerator.generateSequence(Mockito.eq("notesSequence"))).thenReturn(noteDto.getId());
        Mockito.when(timeService.now()).thenReturn(noteDto.getCreatedAt());
        // Create
        noteService.create(noteDto);
        Mockito.verify(repository, Mockito.times(1)).save(noteMapper.fromDto(noteDto));
        // InvalidParam
        Assertions.assertThatThrownBy(() -> noteService.create(null)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void update() {
        long patientId = 5L;
        NoteDto noteDto = NoteData.generateNoteDto(patientId);
        noteDto.setNoteContent("update");

        // Update
        Mockito.when(repository.findById(noteDto.getId())).thenReturn(Optional.of(NoteData.generateNote(5L)));
        ZonedDateTime update = NoteData.getCurrentZonedDateTime();
        Mockito.when(timeService.now()).thenReturn(update);
        noteDto.setUpdatedAt(update);
        noteService.update(noteDto);
        Mockito.verify(repository, Mockito.times(1)).save(noteMapper.fromDto(noteDto));

        // NoteNotFound
        Mockito.when(repository.findById(noteDto.getId())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> noteService.update(noteDto)).isInstanceOf(NoteNotFoundException.class);

        // InvalidParam
        Assertions.assertThatThrownBy(() -> noteService.update(null)).isInstanceOf(InvalidParameterException.class);
        noteDto.setId(null);
        Assertions.assertThatThrownBy(() -> noteService.update(noteDto)).isInstanceOf(InvalidParameterException.class);
        noteDto.setId(-1L);
        Assertions.assertThatThrownBy(() -> noteService.update(noteDto)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void delete() {
        long id = 12L;
        // Delete
        Mockito.when(repository.existsById(id)).thenReturn(true);
        noteService.delete(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
        // NoteNotFound
        Mockito.when(repository.existsById(id)).thenReturn(false);
        Assertions.assertThatThrownBy(() -> noteService.delete(id)).isInstanceOf(NoteNotFoundException.class);
        // InvalidParam
        Assertions.assertThatThrownBy(() -> noteService.delete(null)).isInstanceOf(InvalidParameterException.class);
        Assertions.assertThatThrownBy(() -> noteService.delete(-1L)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void searchTermFactors() {
        SearchFactorsRequest searchFactorsRequest = SearchFactorsRequest.builder()
                .patientId(1L)
                .factorsTermList(List.of("test", "test1", "test2"))
                .build();
        Long patientId = searchFactorsRequest.getPatientId();
        // getFactorsResult
        Mockito.when(repository.existsByPatientId(patientId)).thenReturn(true);
        Mockito.when(repository.existsByPatientIdAndNoteContentContains(patientId, "test")).thenReturn(true);
        Mockito.when(repository.existsByPatientIdAndNoteContentContains(patientId, "test1")).thenReturn(true);
        Mockito.when(repository.existsByPatientIdAndNoteContentContains(patientId, "test2")).thenReturn(false);
        Map<String, Boolean> factorsMapTest = Map.of("test", true, "test1", true, "test2", false);
        SearchFactorsResult result = SearchFactorsResult.builder()
                .result(factorsMapTest)
                .build();
        Assertions.assertThat(noteService.searchTermsFactors(searchFactorsRequest)).isEqualTo(result);
        // NoteNotFound
        Mockito.when(repository.existsByPatientId(patientId)).thenReturn(false);
        Assertions.assertThatThrownBy(() -> noteService.searchTermsFactors(searchFactorsRequest)).isInstanceOf(NoteNotFoundException.class);
        // InvalidParam
        Assertions.assertThatThrownBy(() -> noteService.searchTermsFactors(null)).isInstanceOf(InvalidParameterException.class);
    }
}
