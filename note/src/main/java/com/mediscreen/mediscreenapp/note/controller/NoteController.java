package com.mediscreen.mediscreenapp.note.controller;

import com.mediscreen.mediscreenapp.note.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsRequest;
import com.mediscreen.mediscreenapp.note.dto.SearchFactorsResult;
import com.mediscreen.mediscreenapp.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/note")
@Tag(name = "NoteController", description = "CRUD note controller and find terms")
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(NoteService noteService) {
        this.service = noteService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = NoteDto.class)))),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @Operation(description = "Get all notes from a patient")
    public List<NoteDto> getNotesFromPatientId(
            @Parameter(name = "patientId", description = "use to find patient's notes") @PathVariable("id") Long patientId) {
        return service.getByPatientId(patientId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping()
    @Operation(description = "Add a note send in parameter to DataBase")
    public void createNote(
            @Parameter(name = "noteDto", description = "note to add") @RequestBody NoteDto noteDto) {
        service.create(noteDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "NoteNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping()
    @Operation(description = "Update note find with the id of the parameter send and his content in the DataBase")
    public void updateNote(
            @Parameter(name = "noteDto", description = "note to update, holding noteId to identify the note") @RequestBody NoteDto noteDto) {
        service.update(noteDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "NoteNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @Operation(description = "Delete a note of the DataBase")
    public void deleteNote(
            @Parameter(name = "id", description = "id of the note to delete") @PathVariable Long id) {
        service.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchFactorsResult.class))),
            @ApiResponse(responseCode = "404", description = "NoteNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/searchTermsFactors")
    @Operation(description = "Check if terms are present in patient's notes")
    public SearchFactorsResult searchTermsFactors(
            @Parameter(name = "request", description = "Hold the patientId to get notes, and the list of terms to check in those notes")
            @RequestBody SearchFactorsRequest request) {
        return service.searchTermsFactors(request);
    }
}
