package com.tp.toneRowMatrixCalculator.models;

import java.util.List;

public class ToneRowMeta {
    ToneRow toneRow;
    Work work;
    List<Composer> composers;

    public ToneRowMeta(ToneRow toneRow, Work work, List<Composer> composers) {
        this.toneRow = toneRow;
        this.work = work;
        this.composers = composers;
    }

    public ToneRow getToneRow() {
        return toneRow;
    }

    public void setToneRow(ToneRow toneRow) {
        this.toneRow = toneRow;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<Composer> getComposers() {
        return composers;
    }

    public void setComposers(List<Composer> composers) {
        this.composers = composers;
    }
}
