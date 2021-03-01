package com.tp.toneRowMatrixCalculator.controllers;

public class ToneRowRequest {
    Integer workId;
    Integer[] noteOrder;

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer[] getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(Integer[] noteOrder) {
        this.noteOrder = noteOrder;
    }

}
