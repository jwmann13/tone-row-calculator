package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface ComposerDao {
    Composer createComposer(String composer);

    Map<Integer, Composer> getAllComposers();

    Composer getComposerById(Integer id);

    Composer getComposerByName(String composer);

    boolean exists(String composer);

    Composer deleteComposerById(Integer composerId);

    Composer updateComposer(Integer composerId, String name);
}
