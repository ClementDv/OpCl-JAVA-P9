package com.mediscreen.mediscreenapp.patient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    private String birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;
}
