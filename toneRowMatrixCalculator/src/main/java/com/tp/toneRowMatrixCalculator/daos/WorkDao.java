package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Work;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Component
@Profile({"production", "daoTesting", "serviceTesting"})
public interface WorkDao {
    Work createWork(String workTitle);

    Map<Integer, Work> getAllWorks();

    Work getWorkById(Integer workId);

    Work getWorkByTitle(String workTitle);

    boolean exists(String workTitle);

    Work deleteWorkById(Integer workId);

    Work updateWork(Integer workId, String work);
}
