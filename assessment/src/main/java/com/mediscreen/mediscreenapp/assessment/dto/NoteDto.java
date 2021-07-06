package com.mediscreen.mediscreenapp.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NoteDto", description = "NoteDto content")
public class NoteDto {

    private Long id;

    @NotEmpty
    private Long patientId;

    @NotEmpty
    private String noteContent;
}
