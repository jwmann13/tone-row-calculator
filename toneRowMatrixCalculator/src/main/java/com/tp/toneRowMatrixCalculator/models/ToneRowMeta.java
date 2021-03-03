package com.tp.toneRowMatrixCalculator.models;

import java.util.List;

public class ToneRowMeta {
    Integer toneRowId;
    Work work;
    List<Composer> composers;

    public ToneRowMeta(Integer toneRowId, Work work, List<Composer> composers) {
        this.toneRowId = toneRowId;
        this.work = work;
        this.composers = composers;
    }

    public Integer getToneRowId() {
        return toneRowId;
    }

    public void setToneRowId(Integer toneRowId) {
        this.toneRowId = toneRowId;
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
