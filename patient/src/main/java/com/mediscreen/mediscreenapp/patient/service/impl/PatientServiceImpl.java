package com.mediscreen.mediscreenapp.patient.service.impl;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.entity.Patient;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;
import com.mediscreen.mediscreenapp.patient.mapper.PatientMapper;
import com.mediscreen.mediscreenapp.patient.repository.PatientRepository;
import com.mediscreen.mediscreenapp.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    private final PatientMapper mapper;

    @Autowired
    public PatientServiceImpl(PatientRepository repository, PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PatientDto getPatient(Long id) {
        Optional<Patient> patient = repository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException(id);
        }
        log.info("Patient found with the follow id : {}", id);
        return mapper.toDto(patient.get());
    }

    @Override
    public List<PatientDto> getAllPatients() {
        List<Patient> patientList = repository.findAll();
        return patientList.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void addPatient(PatientDto patientDto) {
        if (!ObjectUtils.isEmpty(patientDto)) {
            Patient patient = mapper.fromDto(patientDto);
            repository.save(patient);
            log.info("Patient been successfully saved");
        } else {
            log.error("Patient is empty");
            throw new InvalidParameterException("Patient is empty");
        }
    }

    @Override
    public void updatePatient(PatientDto patientDto) {
        if (!ObjectUtils.isEmpty(patientDto)) {
            Optional<Patient> patient = repository.findById(patientDto.getId());
            if (patient.isEmpty()) {
                throw new PatientNotFoundException(patientDto.getId());
            }
            mapper.fromDto(patient.get(), patientDto);
            repository.save(patient.get());
            log.info("Patient been successfully updated");
        } else {
            log.error("Patient is empty");
            throw new InvalidParameterException("Patient is empty");
        }
    }

    @Override
    public void deletePatient(Long id) {
        try {
            repository.deleteById(id);
            log.info("Patient been successfully deleted");
        }
        catch (EmptyResultDataAccessException e) {
            throw new PatientNotFoundException(id);
        }
    }
}
