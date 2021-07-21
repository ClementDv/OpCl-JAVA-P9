package com.mediscreen.mediscreenapp.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NoteDto", description = "NoteDto content")
public class NoteDto {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Long patientId;

    @Schema(name = "createdAt", description = "creation date and time")
    private ZonedDateTime createdAt;

    @Schema(name = "updatedAt", description = "Last update with date and time")
    private ZonedDateTime updatedAt;

    @NotEmpty
    private String noteContent;
}
