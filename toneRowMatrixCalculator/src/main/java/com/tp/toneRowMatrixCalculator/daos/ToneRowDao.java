package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Matrix;
import com.tp.toneRowMatrixCalculator.models.ToneRow;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface ToneRowDao {

    Map<Integer, ToneRow> getAllToneRows();

    ToneRow getToneRowById(Integer toneRowId);

    ToneRow createToneRow(Integer workId);

    ToneRow deleteToneRowById(Integer toneRowId);
}
