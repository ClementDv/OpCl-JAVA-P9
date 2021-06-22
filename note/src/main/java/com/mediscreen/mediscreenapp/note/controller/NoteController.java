package com.mediscreen.mediscreenapp.note.controller;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/note")
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(NoteService noteService) {
        this.service = noteService;
    }

    @GetMapping("/{id}")
    public List<NoteDto> getNotesFromPatientId(@PathVariable("id") Long patientId) {
        return service.getByPatientId(patientId);
    }

    @PostMapping()
    public void createNote(@RequestBody NoteDto noteDto) {
        service.create(noteDto);
    }

    @PostMapping("/searchFactors")
    public SearchFactorsResult searchFactors(@RequestBody SearchFactorsRequest request) {
        return service.searchFactors(request);
    }

    @PutMapping()
    public void updateNote(@RequestBody NoteDto noteDto) {
        service.update(noteDto);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        service.delete(id);
    }
}
