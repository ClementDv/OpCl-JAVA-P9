package com.mediscreen.mediscreenapp.note.controller;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(NoteService noteService) {
        this.service = noteService;
    }

    @GetMapping("/note/{id}")
    public List<NoteDto> getNotesFromPatientId(@PathVariable("id") Long patientId) {
        return service.getNotesFromPatientId(patientId);
    }

    @PostMapping("/note")
    public void addNote(@RequestBody NoteDto noteDto) {
        service.addNote(noteDto);
    }

    @PutMapping("/note")
    public void updateNote(@RequestBody NoteDto noteDto) {
        service.updateNote(noteDto);
    }

    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable Long id) {
        service.deleteNote(id);
    }
}
