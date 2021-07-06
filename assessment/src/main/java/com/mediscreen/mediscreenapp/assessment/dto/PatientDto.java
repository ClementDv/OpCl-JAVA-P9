package com.mediscreen.mediscreenapp.assessment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PatientDto", description = "PatientDto content")
public class PatientDto {

    @Schema(name = "id")
    private Long id;

    @NotEmpty
    @Schema(name = "firstName")
    private String firstName;

    @NotEmpty
    @Schema(name = "lastName")
    private String lastName;

    @NotEmpty
    @Schema(name = "gender")
    private String gender;

    @Schema(name = "address", description = "Optional")
    private String address;

    @NotEmpty
    @Schema(name = "birthDate")
    private LocalDate birthDate;

    @Schema(name = "phoneNumber", description = "Optional")
    private String phoneNumber;
}
