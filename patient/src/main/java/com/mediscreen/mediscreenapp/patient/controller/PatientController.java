package com.mediscreen.mediscreenapp.patient.controller;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public PatientDto getPatient(@PathVariable("id") Long id) {
        return patientService.getById(id);
    }

    @GetMapping("/list")
    public List<PatientDto> getAllPatients() {
        return patientService.getAll();
    }

    @PostMapping()
    public void createPatient(@RequestBody PatientDto patientDto) {
        patientService.create(patientDto);
    }

    @PutMapping()
    public void updatePatient(@RequestBody PatientDto patientDto) {
        patientService.update(patientDto);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.delete(id);
    }
}
