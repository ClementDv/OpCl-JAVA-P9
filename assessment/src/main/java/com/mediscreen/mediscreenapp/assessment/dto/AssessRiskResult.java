package com.mediscreen.mediscreenapp.assessment.dto;

import com.mediscreen.mediscreenapp.assessment.domain.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessRiskResult {
    private Long patientId;
    private RiskLevel riskLevel;
    private List<String> factorsTermsMatch;
}
