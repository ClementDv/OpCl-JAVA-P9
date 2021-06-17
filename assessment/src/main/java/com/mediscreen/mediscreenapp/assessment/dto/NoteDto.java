package com.mediscreen.mediscreenapp.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private int patientId;
    private String noteContent;
}
