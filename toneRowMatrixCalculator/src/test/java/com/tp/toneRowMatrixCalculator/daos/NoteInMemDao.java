package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Note;
import com.tp.toneRowMatrixCalculator.models.NoteInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("serviceTesting")
public class NoteInMemDao implements NoteDao {
    Map<Integer, List<Note>> allNotes;

    NoteInMemDao() {
        allNotes = new HashMap<>();
    }

    public void clear() {
        allNotes.clear();
    }

    @Override
    public Note createNote(int pitchClass, int orderIndex, Integer toneRowId) {
        Note toAdd = new Note();

        toAdd.setNoteInfo(NoteInfo.getByValue(pitchClass));
        toAdd.setOrderIndex(orderIndex);
        toAdd.setToneRowId(toneRowId);

        int id = 0;
        for (Integer key : allNotes.keySet()) {
            for (Note n : allNotes.get(key)) {
                if (n.getNoteId() > id) {
                    id = n.getNoteId();
                }
            }
        }
        id++;

        toAdd.setNoteId(id);

        allNotes.computeIfAbsent(toneRowId, k -> new ArrayList<>());
        allNotes.get(toneRowId).add(orderIndex, toAdd);

        return toAdd;
    }

    @Override
    public List<Note> getNotesForToneRow(Integer toneRowId) {
        return allNotes.get(toneRowId);
    }

    @Override
    public List<Note> deleteNotesForToneRow(Integer toneRowId) {
        throw new UnsupportedOperationException();
    }
}
