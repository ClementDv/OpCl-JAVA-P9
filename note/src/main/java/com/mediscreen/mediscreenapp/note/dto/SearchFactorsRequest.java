package com.mediscreen.mediscreenapp.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SearchFactorsRequest", description = "Request to search factors terms in notes")
public class SearchFactorsRequest {

    @NotNull
    @Schema(name = "patientId", description = "use to get patient's notes")
    private Long patientId;

    @NotEmpty
    @Schema(name = "factorsList", description = "list of all terms to check")
    private List<@NotNull String> factorsTermList;
}
