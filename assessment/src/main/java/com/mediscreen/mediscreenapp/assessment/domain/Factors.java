package com.mediscreen.mediscreenapp.assessment.domain;

import lombok.Getter;

@Getter
public enum Factors {
    HEMOGLOBIN_A1C("hemoglobin a1c", "hemoglobine a1c"),
    MICROALBUMIN("microalbumin", "microalbumine"),
    HEIGHT("height", "taille"),
    WEIGHT("weight", "poids"),
    SMOKER("smoker", "fumeur"),
    ABNORMAL("abnormal", "anormal"),
    CHOLESTEROL("cholesterol"),
    RELAPSE("relapse", "rechute"),
    DIZZINESS("dizziness", "vertige"),
    REACTION("reaction"),
    ANTIBODIES("antibodies", "anticorps"),
    ;

    private final String[] terms;

    Factors(String... terms) {
        this.terms = terms;
    }
}
