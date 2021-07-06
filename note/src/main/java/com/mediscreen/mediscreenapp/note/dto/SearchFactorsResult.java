package com.mediscreen.mediscreenapp.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SearchFactorsResult", description = "The result of the searchFactors")
public class SearchFactorsResult {

    @Schema(name = "result", description = "The map of all term if they're present or not," +
            " key = Term, value = True means present, False means it's not ")
    private Map<String, Boolean> result;
}
