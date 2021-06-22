package com.mediscreen.mediscreenapp.note.repository;

import com.mediscreen.mediscreenapp.note.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {
    List<Note> getAllByPatientId(Long id);

    boolean existsById(Long id);

    boolean existsByPatientId(Long id);

    Boolean existsByPatientIdAndNoteContentContains(Long patientId, String factors);
}
