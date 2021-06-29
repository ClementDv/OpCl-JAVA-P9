package com.mediscreen.mediscreenapp.patient.service;

import com.mediscreen.mediscreenapp.patient.data.PatientData;
import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.entity.Patient;
import com.mediscreen.mediscreenapp.patient.exception.PatientNotFoundException;
import com.mediscreen.mediscreenapp.patient.mapper.PatientMapper;
import com.mediscreen.mediscreenapp.patient.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class PatientServiceTest {

    @MockBean
    private PatientRepository repository;

    private final PatientService service;

    private final PatientMapper mapper;

    @Autowired
    PatientServiceTest(PatientService service, PatientMapper patientMapper, PatientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Test
    void getByIdTest() {
        // Patient found
        Long id = 3L;
        Patient patientTest = PatientData.generatePatient(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(patientTest));
        Assertions.assertThat(service.getById(id)).isEqualTo(mapper.toDto(patientTest));
        // Not found
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> service.getById(id)).isInstanceOf(PatientNotFoundException.class);
    }

    @Test
    void getAll() {
        List<Patient> patientListTest = PatientData.generatePatientList(5);
        Mockito.when(repository.findAll()).thenReturn(patientListTest);
        List<PatientDto> patientDtoList = patientListTest.stream().map(mapper::toDto).collect(Collectors.toList());
        Assertions.assertThat(service.getAll()).isEqualTo(patientDtoList);
    }

    @Test
    void create() {
        Long id = 3L;
        PatientDto patientTest = PatientData.generatePatientDto(id);
        service.create(patientTest);
        Mockito.verify(repository, Mockito.times(1)).save(mapper.fromDto(patientTest));
        // NullSend
        Assertions.assertThatThrownBy(() -> service.create(null)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void update() {
        Long id = 3L;
        PatientDto patientTest = PatientData.generatePatientDto(id);
        // PatientUpdate
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(mapper.fromDto(patientTest)));
        service.update(patientTest);
        Mockito.verify(repository, Mockito.times(1)).save(mapper.fromDto(patientTest));
        // PatientNotFound
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> service.update(patientTest)).isInstanceOf(PatientNotFoundException.class);
        // NullSend
        Assertions.assertThatThrownBy(() -> service.update(null)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void delete() {
        // PatientDelete
        Long id = 19L;
        service.delete(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
        // PatientNotFound
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(id);
        Assertions.assertThatThrownBy(() -> service.delete(id)).isInstanceOf(PatientNotFoundException.class);
    }
}
