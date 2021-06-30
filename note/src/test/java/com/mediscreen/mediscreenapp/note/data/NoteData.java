package com.mediscreen.mediscreenapp.note.data;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.entity.Note;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NoteData {

    private static Random random = new Random(10L);

    public static Note generateNote(long patientId) {
        ZonedDateTime create = getCurrentZonedDateTime();
        return Note.builder()
                .id(1 + ((long) random.nextDouble() * (1000L - 1L)))
                .patientId(patientId)
                .noteContent("Create for test")
                .createdAt(create)
                .updatedAt(create)
                .build();
    }

    public static NoteDto generateNoteDto(long patientId) {
        ZonedDateTime create = getCurrentZonedDateTime();
        return NoteDto.builder()
                .id(1 + ((long) random.nextDouble() * (1000L - 1L)))
                .patientId(patientId)
                .noteContent("Create for test")
                .createdAt(create)
                .updatedAt(create)
                .build();
    }

    public static List<Note> generateNoteList(int maxSize, long patientId) {
        return  IntStream.range(1, maxSize).mapToObj(i -> generateNote(patientId)).collect(Collectors.toList());
    }

    public static List<NoteDto> generateNoteDtoList(int maxSize, long patientId) {
        return  IntStream.range(1, maxSize).mapToObj(i -> generateNoteDto(patientId)).collect(Collectors.toList());
    }

    public static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).withNano(0);
    }
}
