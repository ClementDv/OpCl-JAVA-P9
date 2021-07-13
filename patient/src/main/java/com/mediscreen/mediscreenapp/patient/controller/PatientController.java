package com.mediscreen.mediscreenapp.patient.controller;

import com.mediscreen.mediscreenapp.patient.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;
import com.mediscreen.mediscreenapp.patient.service.PatientService;
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

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/patient")
@Tag(name = "PatientController", description = "CRUD patient controller")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class))),
            @ApiResponse(responseCode = "404", description = "PatientNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Get patient by id")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @Parameter(name = "id", description = "Id of the patient to find") @PathVariable("id") Long id) {
        return patientService.getById(id);
    }

    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientDto.class))))
    @Operation(description = "Get all patient")
    @GetMapping("/")
    public List<PatientDto> getAllPatients() {
        return patientService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Add patient send in parameter to DataBase")
    @PostMapping()
    public void createPatient(
            @Parameter(name = "patientDto", description = "patient to add") @RequestBody PatientDto patientDto) {
        patientService.create(patientDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "PatientNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "InvalidParameterException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Update patient find with the id of the parameter send and his content")
    @PutMapping()
    public void updatePatient(
            @Parameter(name = "patientDto", description = "patient to update, holding patientId to identify the patient")
            @RequestBody PatientDto patientDto) {
        patientService.update(patientDto);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "PatientNotFoundException",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(description = "Delete a patient of the DataBase")
    @DeleteMapping("/{id}")
    public void deletePatient(
            @Parameter(name = "id", description = "id of the patient to delete") @PathVariable Long id) {
        patientService.delete(id);
    }
}
