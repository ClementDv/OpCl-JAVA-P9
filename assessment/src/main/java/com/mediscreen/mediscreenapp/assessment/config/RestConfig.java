package com.mediscreen.mediscreenapp.assessment.config;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public JSONObject getJSONObject() {
        return new JSONObject();
    }

    @Bean
    public HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }
}
