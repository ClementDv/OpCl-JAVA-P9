package com.mediscreen.mediscreenapp.patient.mapper;

import com.mediscreen.mediscreenapp.patient.dto.PatientDto;
import com.mediscreen.mediscreenapp.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient fromDto(PatientDto patientDto);

    void fromDto(@MappingTarget Patient patient, PatientDto patientDto);
}
