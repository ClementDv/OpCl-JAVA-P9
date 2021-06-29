package com.mediscreen.mediscreenapp.patient.data;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PatientData {

    public static Patient generatePatient(Long id) {
        return Patient.builder()
                .id(id)
                .firstName("Test" + id)
                .lastName("TestName")
                .gender("male")
                .address("TestAdress")
                .birthDate(LocalDate.now())
                .phoneNumber("TestPhoneNumber")
                .build();
    }

    public static PatientDto generatePatientDto(Long id) {
        return PatientDto.builder()
                .id(id)
                .firstName("Test" + id)
                .lastName("TestName")
                .gender("male")
                .address("TestAdress")
                .birthDate(LocalDate.now())
                .phoneNumber("TestPhoneNumber")
                .build();
    }

    public static List<Patient> generatePatientList(int maxSize) {
        return IntStream.range(1, maxSize).mapToObj(i -> generatePatient((long) i)).collect(Collectors.toList());
    }

    public static List<PatientDto> generatePatientDtoList(int maxSize) {
        return IntStream.range(1, maxSize).mapToObj(i -> generatePatientDto((long) i)).collect(Collectors.toList());
    }
}
