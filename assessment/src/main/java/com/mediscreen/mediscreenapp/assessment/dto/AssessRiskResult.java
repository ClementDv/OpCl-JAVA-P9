package com.mediscreen.mediscreenapp.assessment.dto;

import com.mediscreen.mediscreenapp.assessment.domain.RiskLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AssessRiskResult", description = "result of the assessment with the Risk Level and the factors that have matched")
public class AssessRiskResult {

    @Schema(name = "patientId", description = "the Id of the patient assess")
    private Long patientId;

    @Schema(name = "riskLevel", description = "the level of the risk assess")
    private RiskLevel riskLevel;

    @Schema(name = "factorsMatch", description = "the factors that have matched")
    private List<String> factorsMatch;
}
