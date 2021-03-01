package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Work;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface WorkDao {
    Work createWork(String workTitle);

    Map<Integer, Work> getAllWorks();

    Work getWorkById(Integer workId);

    Work getWorkByTitle(String workTitle);

    boolean exists(String workTitle);
}
