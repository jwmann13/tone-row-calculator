package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Note;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface NoteDao {
    Note createNote(int pitchClass, int orderIndex, Integer toneRowId);

    List<Note> getNotesForToneRow(Integer toneRowId);

    List<Note> deleteNotesForToneRow(Integer toneRowId);
}
