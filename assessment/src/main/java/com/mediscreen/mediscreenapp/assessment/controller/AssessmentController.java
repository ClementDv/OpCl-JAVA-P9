package com.mediscreen.mediscreenapp.assessment.controller;

import com.mediscreen.mediscreenapp.assessment.dto.AssessRiskResult;
import com.mediscreen.mediscreenapp.assessment.dto.ErrorResponse;
import com.mediscreen.mediscreenapp.assessment.service.AssessmentService;
import io.micrometer.core.lang.NonNullApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AssessmentController", description = "controller which assess using values from others module")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessRiskResult.class))),
            @ApiResponse(responseCode = "404/422", description = "RestServiceTransmitException," +
                    " use to catch exception from the others module and rethrow them",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/assess/{id}")
    @Operation(description = "assess a patient to determine Diabetes risks")
    public AssessRiskResult assessmentPatient(
            @Parameter(name = "patientId", description = "if of the patient to assess") @PathVariable("id") Long patientId) {
        return assessmentService.assessmentPatient(patientId);
    }
}
