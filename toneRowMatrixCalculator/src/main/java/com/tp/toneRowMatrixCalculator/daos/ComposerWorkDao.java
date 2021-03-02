package com.tp.toneRowMatrixCalculator.daos;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface ComposerWorkDao {
    boolean exists(Integer workId, Integer composerId);
    Integer[] getComposerWork (Integer workId, Integer composerId);
    void createComposerWork(Integer workId, Integer composerId);
}
