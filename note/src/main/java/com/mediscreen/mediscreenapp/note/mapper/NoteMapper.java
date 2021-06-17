package com.mediscreen.mediscreenapp.note.mapper;

import com.mediscreen.mediscreenapp.note.dto.NoteDto;
import com.mediscreen.mediscreenapp.note.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note fromDto(NoteDto noteDto);

    NoteDto toDto(Note note);

    void fromDto(@MappingTarget Note note, NoteDto noteDto);
}
