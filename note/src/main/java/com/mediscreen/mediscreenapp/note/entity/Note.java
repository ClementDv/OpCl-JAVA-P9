package com.mediscreen.mediscreenapp.note.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "note")
public class Note {

    @Transient
    public static final String SEQUENCE_NAME = "notesSequence";

    @Id
    private Long id;

    @Field(name = "createdAt")
    private ZonedDateTime createdAt;

    @Field(name = "updatedAt")
    private ZonedDateTime updatedAt;

    @Field(name = "patientId")
    private Long patientId;

    @Field(name = "noteContent")
    private String noteContent;

}
