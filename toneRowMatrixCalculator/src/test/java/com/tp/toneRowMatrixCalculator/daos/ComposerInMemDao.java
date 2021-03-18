package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Profile("serviceTesting")
public class ComposerInMemDao implements ComposerDao{
    @Override
    public Composer createComposer(String composer) {
        return null;
    }

    @Override
    public Map<Integer, Composer> getAllComposers() {
        return null;
    }

    @Override
    public Composer getComposerById(Integer id) {
        return null;
    }

    @Override
    public Composer getComposerByName(String composer) {
        return null;
    }

    @Override
    public boolean exists(String composer) {
        return false;
    }

    @Override
    public Composer deleteComposerById(Integer composerId) {
        return null;
    }

    @Override
    public Composer updateComposer(Integer composerId, String name) {
        return null;
    }
}
