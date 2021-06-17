package com.mediscreen.mediscreenapp.note.service.impl;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.entity.Note;
import com.mediscreen.mediscreenapp.note.exception.NoteNotFoundException;
import com.mediscreen.mediscreenapp.note.mapper.NoteMapper;
import com.mediscreen.mediscreenapp.note.repository.NoteRepository;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import com.mediscreen.mediscreenapp.note.service.SequenceServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteMapper mapper;

    private final NoteRepository repository;

    private final SequenceServiceGenerator sequenceServiceGenerator;

    @Autowired
    public NoteServiceImpl(NoteMapper mapper, NoteRepository repository, SequenceServiceGenerator sequenceServiceGenerator) {
        this.mapper = mapper;
        this.repository = repository;
        this.sequenceServiceGenerator = sequenceServiceGenerator;
    }

    @Override
    public List<NoteDto> getNotesFromPatientId(Long patientId) {
        if (patientId == null || patientId <= 0) {
            throw new InvalidParameterException("Invalid Id");
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
    public void addNote(NoteDto noteDto) {
        if (ObjectUtils.isEmpty(noteDto)) {
            throw new InvalidParameterException("Invalid Note");
        }
        Note entity = mapper.fromDto(noteDto);
        entity.setId(sequenceServiceGenerator.generateSequence(Note.SEQUENCE_NAME));
        log.info("Note been successfully added");
        repository.save(entity);
    }

    @Override
    public void updateNote(NoteDto noteDto) {
        if (ObjectUtils.isEmpty(noteDto) || noteDto.getId() == null || noteDto.getId() <= 0) {
            throw new InvalidParameterException("Invalid Note");
        }
        if (!repository.existsById(noteDto.getId())) {
            throw new NoteNotFoundException(noteDto.getId());
        }
        Note entity = mapper.fromDto(noteDto);
        log.info("Note been successfully updated");
        repository.save(entity);
    }

    @Override
    public void deleteNote(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidParameterException("Invalid id");
        }
        log.info("Note been successfully deleted");
        repository.deleteById(id);
    }
}
