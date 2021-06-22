package com.mediscreen.mediscreenapp.note.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFactorsRequest {
    @NotNull
    private Long patientId;

    @NotEmpty
    private List<@NotNull String> factorsList;
}
