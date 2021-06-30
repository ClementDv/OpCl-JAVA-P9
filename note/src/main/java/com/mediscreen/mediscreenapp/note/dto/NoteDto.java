package com.mediscreen.mediscreenapp.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private Long patientId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String noteContent;
}
