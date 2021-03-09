package com.tp.toneRowMatrixCalculator.services;

import com.tp.toneRowMatrixCalculator.exceptions.InvalidIdException;
import com.tp.toneRowMatrixCalculator.models.*;
import com.tp.toneRowMatrixCalculator.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile({"production", "daoTesting", "serviceTesting"})
public class MatrixService {
    @Autowired
    ToneRowDao toneRowDao;

    @Autowired
    NoteDao noteDao;

    @Autowired
    WorkDao workDao;

    @Autowired
    ComposerDao composerDao;

    @Autowired
    ComposerWorkDao composerWorkDao;

    // GETs for ToneRows and Matrices
    public Map<Integer, Matrix> getAllMatrices() {
        Map<Integer, ToneRow> allToneRows = getAllToneRows();

        Map<Integer, Matrix> toReturn = new HashMap<>();
        for (Integer key : allToneRows.keySet()) {
            toReturn.put(key, allToneRows.get(key).generateMatrix());
        }

        return toReturn;
    }

    public Map<Integer, ToneRow> getAllToneRows() {
        Map<Integer, ToneRow> toReturn = toneRowDao.getAllToneRows();

        for (Integer key : toReturn.keySet()) {
            setNoteOrderForToneRow(toReturn.get(key));
        }

        return toReturn;
    }

    public Matrix getMatrixById(Integer id) throws InvalidIdException {
        ToneRow toMap = getToneRowById(id);
        if (toMap == null) throw new InvalidIdException("No Matrix with id: " + id);

        return toMap.generateMatrix();
    }

    public ToneRow getToneRowById(Integer id) throws InvalidIdException {
        ToneRow toReturn = toneRowDao.getToneRowById(id);
        if (toReturn == null) throw new InvalidIdException("No Matrix with id: " + id);

        setNoteOrderForToneRow(toReturn);

        return toReturn;
    }

    // helper to set notes on each ToneRow
    private void setNoteOrderForToneRow(ToneRow toSet) {
        List<Note> noteList = noteDao.getNotesForToneRow(toSet.getToneRowId());

        Note[] orderedNotes = new Note[12];
        for (Note toOrder : noteList) {
            orderedNotes[toOrder.getOrderIndex()] = toOrder;
        }

        toSet.setNoteOrder(orderedNotes);
    }

    // POST for ToneRows
    public ToneRow createToneRow(Integer[] noteOrder, Integer workId) {
        if (noteOrder == null) {
            throw new IllegalArgumentException("Cannot create a Tone Row with a null set of notes");
        }
        if (Arrays.stream(noteOrder).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Cannot create a Tone Row with any null notes");
        }
        if (noteOrder.length != 12) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot create a Tone Row with %s 12 notes",
                            noteOrder.length < 12 ? "less than" : "more than"
                    )
            );
        }
        if (hasDuplicates(noteOrder)) {
            throw new IllegalArgumentException("Cannot create a Tone Row with duplicate notes");
        }

        ToneRow newToneRow = toneRowDao.createToneRow(workId);

        newToneRow.setNoteOrder(
                createNotesOnToneRow(
                        noteOrder,
                        newToneRow.getToneRowId()
                )
        );

        return newToneRow;
    }

    // helper to prevent duplicate notes on ToneRow
    private boolean hasDuplicates(Integer[] noteOrder) {
        Set<Integer> seen = new HashSet<Integer>();
        for (Integer i : noteOrder) {
            if (seen.contains(i)) return true;
            seen.add(i);
        }
        return false;
    }

    // helper to create notes on each ToneRow
    private Note[] createNotesOnToneRow(Integer[] noteOrder, Integer toneRowId) {
        Note[] toReturn = new Note[12];
        for (int orderIndex = 0; orderIndex < 12; orderIndex++) {
            int pitchClass = noteOrder[orderIndex];
            toReturn[orderIndex] = noteDao.createNote(pitchClass, orderIndex, toneRowId);
        }
        return toReturn;
    }

    // DELETE for ToneRow
    public ToneRow deleteToneRow(Integer id) throws InvalidIdException {
        if (id == null) throw new InvalidIdException("Cannot delete with null id");
        noteDao.deleteNotesForToneRow(id);
        ToneRow deleted = toneRowDao.deleteToneRowById(id);
        if (deleted == null) throw new InvalidIdException("No Tone Row with that id exists");
        composerWorkDao.deleteComposerWorkByWorkId(deleted.getWorkId());
        workDao.deleteWorkById(deleted.getWorkId());
        return deleted;
    }

    // GETs for Works and Composers
    public Map<Integer, Work> getAllWorks() {
        return workDao.getAllWorks();
    }

    public Work getWorkByTitle(String title) {
        return workDao.getWorkByTitle(title);
    }

    public Work getWorkById(Integer id) {
        return workDao.getWorkById(id);
    }

    public Map<Integer, Composer> getAllComposers() {
        return composerDao.getAllComposers();
    }

    public Composer getComposerByName(String name) {
        return composerDao.getComposerByName(name);
    }

    public Composer getComposerById(Integer id) {
        return composerDao.getComposerById(id);
    }

    // POSTs for Works and Composers
    public Work createWork(String workTitle) {
        Work toReturn;
//        if (workDao.exists(workTitle)) {
//            toReturn = workDao.getWorkByTitle(workTitle);
//        } else {
            toReturn = workDao.createWork(workTitle);
//        }
        return toReturn;
    }

    public Composer createComposer(String composerName) {
        Composer toReturn;
        if (composerDao.exists(composerName)) {
            toReturn = composerDao.getComposerByName(composerName);
        } else {
            toReturn = composerDao.createComposer(composerName);
        }
        return toReturn;
    }

    public List<ComposerWork> getComposerWorksByWorkId(Integer id) {
        List<ComposerWork> toReturn;
        toReturn = composerWorkDao.getComposerWorkByWorkId(id);
        return toReturn;
    }

    public ComposerWork createComposerWork(Work work, Composer composer) {
        if (composerWorkDao.exists(work.getWorkId(), composer.getComposerId())) {
            return composerWorkDao.getComposerWork(work.getWorkId(), composer.getComposerId());
        } else {
            return composerWorkDao.createComposerWork(work, composer);
        }
    }

    public ToneRowMeta getToneRowMeta(Integer id) throws InvalidIdException {
        if (id == null) throw new InvalidIdException("Cannot get tonerow with null id");

        ToneRow toMap = toneRowDao.getToneRowById(id);

        if (toMap == null) throw new InvalidIdException("No tonerow with id " + id);
        if (toMap.getWorkId() == null) return null;

        Work associatedWork = workDao.getWorkById(toMap.getWorkId());

        List<ComposerWork> composersMap = getComposerWorksByWorkId(associatedWork.getWorkId());

        List<Composer> associatedComposers = new ArrayList<>();

        for (ComposerWork cw : composersMap) {
            associatedComposers.add(getComposerById(cw.getComposerId()));
        }

        return new ToneRowMeta(toMap, associatedWork, associatedComposers);
    }

    public ToneRowMeta createToneRowWithDetails(Integer[] noteOrder, String work, List<String> composers) {
        List<Composer> createdComposers = new ArrayList<>();
        for (String composer : composers) {
            createdComposers.add(createComposer(composer));
        }
        Work createdWork = createWork(work);

//        List<ComposerWork> createdComposerWorks = new ArrayList<>();
        for (Composer c : createdComposers) {
//            createdComposerWorks.add(
            createComposerWork(createdWork, c);
//            );
        }

        ToneRow createdToneRow = createToneRow(noteOrder, createdWork.getWorkId());

        return new ToneRowMeta(createdToneRow, createdWork, createdComposers);
    }

    public List<ToneRowMeta> getAllToneRowMeta() {
        List<ToneRowMeta> toReturn = new ArrayList<>();
        Map<Integer, ToneRow> toMap = getAllToneRows();

        for (Integer key : toMap.keySet()) {
            Work associatedWork = getWorkById(toMap.get(key).getWorkId());
            List<ComposerWork> composerWorks = getComposerWorksByWorkId(associatedWork.getWorkId());
            List<Composer> associatedComposers = new ArrayList<>();
            for (ComposerWork cw : composerWorks) {
                associatedComposers.add(getComposerById(cw.getComposerId()));
            }

            toReturn.add(new ToneRowMeta(toMap.get(key), associatedWork, associatedComposers));
        }

        return toReturn;
    }
}
