package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Work;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Profile("serviceTesting")
public class WorkInMemDao implements WorkDao {
    @Override
    public Work createWork(String workTitle) {
        return null;
    }

    @Override
    public Map<Integer, Work> getAllWorks() {
        return null;
    }

    @Override
    public Work getWorkById(Integer workId) {
        return null;
    }

    @Override
    public Work getWorkByTitle(String workTitle) {
        return null;
    }

    @Override
    public boolean exists(String workTitle) {
        return false;
    }

    @Override
    public Work deleteWorkById(Integer workId) {
        throw new UnsupportedOperationException();
    }
}
