package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ComposerDao {
    Composer createComposer(String composer);

    Map<Integer, Composer> getAllComposers();

    Composer getComposerById(Integer id);

    Composer getComposerByName(String composer);

    boolean exists(String composer);
}
