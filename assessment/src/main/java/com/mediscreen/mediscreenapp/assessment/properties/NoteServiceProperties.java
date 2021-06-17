package com.mediscreen.mediscreenapp.assessment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.note-service")
@Data
public class NoteServiceProperties {
    private String url;
}
