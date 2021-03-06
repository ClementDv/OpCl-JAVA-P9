package com.mediscreen.mediscreenapp.note.service.impl;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.entity.Note;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;
import com.mediscreen.mediscreenapp.note.mapper.NoteMapper;
import com.mediscreen.mediscreenapp.note.repository.NoteRepository;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import com.mediscreen.mediscreenapp.note.service.SequenceServiceGenerator;
import com.mediscreen.mediscreenapp.note.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteMapper mapper;

    private final NoteRepository repository;

    private final SequenceServiceGenerator sequenceServiceGenerator;

    private final TimeService timeService;

    @Autowired
    public NoteServiceImpl(NoteMapper mapper, NoteRepository repository, SequenceServiceGenerator sequenceServiceGenerator, TimeService timeService) {
        this.mapper = mapper;
        this.repository = repository;
        this.sequenceServiceGenerator = sequenceServiceGenerator;
        this.timeService = timeService;
    }

    @Override
    public NoteDto getNote(Long noteId) {
        if (noteId == null  || noteId <= 0) {
            throw new InvalidParameterException("Invalid Id");
        }
        Optional<Note> note = repository.findById(noteId);
        if (note.isEmpty()) {
            throw new NoteNotFoundException(noteId);
        }
        log.info("Note found for following id : {}", noteId);
        return mapper.toDto(note.get());
    }

    @Override
    public List<NoteDto> getByPatientId(Long patientId) {
        if (patientId == null || patientId <= 0) {
            throw new InvalidParameterException("Invalid patientId");
        }
        List<NoteDto> noteList = repository.getAllByPatientId(patientId).stream().map(mapper::toDto).collect(Collectors.toList());
        if (noteList.isEmpty()) {
            log.info("No notes found for the following id : {}", patientId);
        }
        else {
            log.info("Note(s) found for the following id : {}", patientId);
        }
        return noteList;
    }

    @Override
    public void create(NoteDto noteDto) {
        if (ObjectUtils.isEmpty(noteDto)) {
            throw new InvalidParameterException("Invalid Note");
        }
        Note entity = Note.builder().build();
        mapper.fromDto(entity, noteDto);
        entity.setId(sequenceServiceGenerator.generateSequence(Note.SEQUENCE_NAME));
        entity.setCreatedAt(timeService.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        log.info("Note been successfully added");
        repository.save(entity);
    }

    @Override
    public void update(NoteDto noteDto) {
        if (ObjectUtils.isEmpty(noteDto) || noteDto.getId() == null || noteDto.getId() <= 0) {
            throw new InvalidParameterException("Invalid Note");
        }
        Note entity = repository.findById(noteDto.getId()).orElse(null);
        if (entity == null) {
            throw new NoteNotFoundException(noteDto.getId());
        }
        ZonedDateTime originalCreatedAt = entity.getCreatedAt();
        mapper.fromDto(entity, noteDto);
        entity.setCreatedAt(originalCreatedAt);
        entity.setUpdatedAt(timeService.now());
        log.info("Note been successfully updated");
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidParameterException("Invalid id");
        }
        if (!repository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        log.info("Note been successfully deleted");
        repository.deleteById(id);
    }

    @Override
    public SearchFactorsResult searchTermsFactors(SearchFactorsRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new InvalidParameterException("Invalid Request");
        }
        Long patientId = request.getPatientId();
        if (repository.existsByPatientId(patientId)) {
            Map<String, Boolean> termResultMap = new HashMap<>();
            for (String factor : request.getFactorsTermList()) {
                termResultMap.put(factor, repository.existsByPatientIdAndNoteContentContains(patientId, factor));
            }
            log.info("Factors search successfully mapped");
            return SearchFactorsResult.builder().result(termResultMap).build();
        }
        throw new NoteNotFoundException("Note(s) not found for specific patientId", patientId);
    }
}
