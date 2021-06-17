package com.mediscreen.mediscreenapp.assessment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.patient-service")
@Data
public class PatientServiceProperties {
    private String url;
}
