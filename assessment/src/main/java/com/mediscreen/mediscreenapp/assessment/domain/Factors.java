package com.mediscreen.mediscreenapp.assessment.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private static final List<String> ALL_FACTORS;

    static {
        List<String> allFactors = new ArrayList<>();
        Arrays.stream(Factors.values()).forEach(e -> allFactors.addAll(Arrays.asList(e.getTerms())));
        ALL_FACTORS = Collections.unmodifiableList(allFactors);
    }

    public static List<String> getAllFactors() {
        return ALL_FACTORS;
    }

    private final String[] terms;

    Factors(String... terms) {
        this.terms = terms;
    }
}
