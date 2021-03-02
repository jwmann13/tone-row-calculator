package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.ComposerWork;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface ComposerWorkDao {
    boolean exists(Integer workId, Integer composerId);
    List<ComposerWork> getComposerWork (Integer workId, Integer composerId);
    List<ComposerWork> getComposerWorkByWorkId (Integer workId);
    void createComposerWork(Integer workId, Integer composerId);
}
