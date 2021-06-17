package com.mediscreen.mediscreenapp.patient.controller;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    private final PatientService service;

    @Autowired
    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/patient/{id}")
    public PatientDto getPatient(@PathVariable("id") Long id) {
        return service.getPatient(id);
    }

    @GetMapping("/patient/list")
    public List<PatientDto> getAllPatients() {
        return service.getAllPatients();
    }

    @PostMapping("/patient")
    public void addPatient(@RequestBody PatientDto patientDto) {
        service.addPatient(patientDto);
    }

    @PutMapping("/patient")
    public void updatePatient(@RequestBody PatientDto patientDto) {
        service.updatePatient(patientDto);
    }

    @DeleteMapping("/patient/{id}")
    public void deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
    }
}
