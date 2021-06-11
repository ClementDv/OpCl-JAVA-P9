package com.mediscreen.mediscreenapp.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private Map<String, Object> metadata;

    public ErrorResponse withMetadata(String key, Object value) {
        if (value == null) {
            this.metadata.remove(key);
        } else {
            this.metadata.put(key, value);
        }
        return this;
    }
}
