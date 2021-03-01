package com.tp.toneRowMatrixCalculator.models;

import java.util.HashMap;
import java.util.Map;

public class ToneRow {
    private Note[] noteOrder;
    private Integer toneRowId;
    private Integer workId;

    public ToneRow() { }

    public ToneRow(ToneRow toCopy) {
        this.noteOrder = toCopy.noteOrder;
        this.toneRowId = toCopy.toneRowId;
        this.workId = toCopy.workId;
    }

    public Note[] getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(Note[] noteOrder) {
        this.noteOrder = noteOrder;
    }

    public Integer getToneRowId() {
        return toneRowId;
    }

    public void setToneRowId(Integer toneRowId) {
        this.toneRowId = toneRowId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Matrix generateMatrix() {
        if (noteOrder == null) return null;

        Matrix generated = new Matrix();

        generated.setPrimes(generatePrimes());
        generated.setInversions(generateInversions());
        generated.setRetrogrades(generateRetrogrades());
        generated.setRetrogradeInversions(generateRetrogradeInversions());

        for (int i = 0; i < generated.matrix.length; i++) {
            for (int j = 0; j < generated.matrix[i].length; j++) {
                generated.matrix[i][j] = this.noteOrder[j]
                        .transpose(
                                (this.invert().noteOrder[i].getPitchClass()
                                        - this.noteOrder[0].getPitchClass()) % 12
                        ).getPitchClass();
            }
        }

        return generated;
    }

    private Map<String, ToneRow> generateRetrogradeInversions() {
        Map<String, ToneRow> toReturn = new HashMap<>();

        for (Note n : noteOrder) {
            ToneRow retrogradeInvertedRow = invert().transpose(n.getPitchClass()).retrograde();
            String label = "RI" + retrogradeInvertedRow.noteOrder[0].getPitchClass();
            toReturn.put(label, retrogradeInvertedRow);
        }

        return toReturn;
    }

    private Map<String, ToneRow> generateRetrogrades() {
        Map<String, ToneRow> toReturn = new HashMap<>();

        for (Note n : invert().noteOrder) {
            ToneRow retrogradeRow = transpose(n.getPitchClass()).retrograde();
            String label = "R" + retrogradeRow.noteOrder[0].getPitchClass();
            toReturn.put(label, retrogradeRow);
        }

        return toReturn;
    }

    private Map<String, ToneRow> generateInversions() {
        Map<String, ToneRow> toReturn = new HashMap<>();

        for (Note n : noteOrder) {
            String label = "I" + n.getPitchClass();
            ToneRow invertedRow = invert().transpose(n.getPitchClass());
            toReturn.put(label, invertedRow);
        }

        return toReturn;
    }

    private Map<String, ToneRow> generatePrimes() {
        Map<String, ToneRow> toReturn = new HashMap<>();

        for (Note n : invert().noteOrder) {
            String label = "P" + n.getPitchClass();
            ToneRow primeRow = transpose(n.getPitchClass());
            toReturn.put(label, primeRow);
        }

        return toReturn;
    }

    private ToneRow transpose(int value) {
        ToneRow toReturn = new ToneRow();
        toReturn.setToneRowId(toneRowId);
        toReturn.setWorkId(workId);

        Note[] transposedNoteOrder = new Note[12];
        for (Note toTranspose : noteOrder) {
            Note transposed = toTranspose.transpose(value);
            transposedNoteOrder[transposed.getOrderIndex()] = transposed;
        }
        toReturn.setNoteOrder(transposedNoteOrder);

        return toReturn;
    }

    private ToneRow invert() {
        ToneRow toReturn = new ToneRow();
        toReturn.setToneRowId(toneRowId);
        toReturn.setWorkId(workId);

        Note[] invertedNoteOrder = new Note[12];
        invertedNoteOrder[0] = noteOrder[0];
        for (int i = 1; i < noteOrder.length; i++) {
            Note inverted = new Note();
            int interval = noteOrder[i - 1].interval(noteOrder[i]);

            // this should not compare to prime row...
            NoteInfo invertedInfo = NoteInfo.getByValue(
                    invertedNoteOrder[i - 1].transpose(-interval).getPitchClass()
            );

            inverted.setNoteId(noteOrder[i].getNoteId());
            inverted.setOrderIndex(noteOrder[i].getOrderIndex());
            inverted.setNoteInfo(invertedInfo);

            invertedNoteOrder[i] = inverted;
        }
        toReturn.setNoteOrder(invertedNoteOrder);

        return toReturn;
    }

    private ToneRow retrograde() {
        ToneRow toReturn = new ToneRow();
        toReturn.setToneRowId(toneRowId);
        toReturn.setWorkId(workId);

        Note[] retrogradeNoteOrder = new Note[12];
        for (int i = 0; i < noteOrder.length / 2; i++) {
            Note temp = noteOrder[i];
            retrogradeNoteOrder[i] = noteOrder[noteOrder.length - i - 1];
            retrogradeNoteOrder[retrogradeNoteOrder.length - i - 1] = temp;
        }
        toReturn.setNoteOrder(retrogradeNoteOrder);

        return toReturn;
    }
}
