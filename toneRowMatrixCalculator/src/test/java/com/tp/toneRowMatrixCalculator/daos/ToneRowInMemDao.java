package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Matrix;
import com.tp.toneRowMatrixCalculator.models.ToneRow;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Profile("serviceTesting")
public class ToneRowInMemDao implements ToneRowDao {

    Map<Integer, ToneRow> allToneRows;

    ToneRowInMemDao() {
        allToneRows = new HashMap<>();
    }

    public void clear() {
        allToneRows.clear();
    }

    @Override
    public Map<Integer, ToneRow> getAllToneRows() {
        return allToneRows;
    }

    @Override
    public ToneRow getToneRowById(Integer toneRowId) {
        return allToneRows.get(toneRowId);
    }

    @Override
    public ToneRow createToneRow(Integer workId) {
        ToneRow toCreate = new ToneRow();

        int toneRowId = 0;
        for (Integer key : allToneRows.keySet()) {
            if (key > toneRowId) {
                toneRowId = key;
            }
        }
        toneRowId++;

        toCreate.setToneRowId(toneRowId);
        toCreate.setWorkId(workId);

        allToneRows.put(toneRowId, toCreate);

        return toCreate;
    }
}
