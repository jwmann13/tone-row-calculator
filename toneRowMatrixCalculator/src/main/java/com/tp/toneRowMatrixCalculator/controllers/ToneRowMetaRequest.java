package com.tp.toneRowMatrixCalculator.controllers;

import java.util.List;

public class ToneRowMetaRequest {
    Integer[] noteOrder;
    String work;
    List<String> composers;

    public Integer[] getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(Integer[] noteOrder) {
        this.noteOrder = noteOrder;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<String> getComposers() {
        return composers;
    }

    public void setComposers(List<String> composers) {
        this.composers = composers;
    }
}
