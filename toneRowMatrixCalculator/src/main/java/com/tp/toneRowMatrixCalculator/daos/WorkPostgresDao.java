package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile({"production", "daoTesting"})
public class WorkPostgresDao implements WorkDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public Work createWork(String workTitle) {
        return template.queryForObject(
                "INSERT INTO \"works\" (\"title\") VALUES (?) RETURNING \"workId\", \"title\"",
                new WorkMapper(),
                workTitle
        );
    }

    @Override
    public Map<Integer, Work> getAllWorks() {
        List<Work> results;
        try {
            results = template.query(
                    "SELECT \"workId\", \"title\" FROM \"works\";\n",
                    new WorkMapper()
            );
        } catch (DataAccessException e) {
            return null;
        }

        Map<Integer, Work> toReturn = new HashMap<>();
        for (Work toMap : results) {
            toReturn.put(toMap.getWorkId(), toMap);
        }
        return toReturn;
    }

    @Override
    public Work getWorkById(Integer workId) {
        List<Work> results;
        try {
            results = template.query(
                    "SELECT \"workId\", \"title\" FROM \"works\" wo WHERE wo.\"workId\" = ?;",
                    new WorkMapper(),
                    workId
            );
        } catch (DataAccessException e) {
            return null;
        }

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Work getWorkByTitle(String workTitle) {
        List<Work> results;
        try {
            results = template.query(
                    "SELECT \"workId\", \"title\" FROM \"works\" wo WHERE wo.\"title\" = ?;",
                    new WorkMapper(),
                    workTitle
            );
        } catch (DataAccessException e) {
            return null;
        }

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean exists(String workTitle) {
        return getWorkByTitle(workTitle) != null;
    }

    @Override
    public Work deleteWorkById(Integer workId) {
        return template.queryForObject("DELETE FROM \"works\"" +
                        "WHERE \"works\".\"workId\" = ?\n" +
                        "RETURNING \"workId\", \"title\";",
                new WorkMapper(),
                workId);
    }

    @Override
    public Work updateWork(Integer workId, String work) {
        return template.queryForObject("UPDATE \"works\"\n" +
                        "SET \"title\" = ?\n" +
                        "WHERE \"works\".\"workId\" = ?" +
                        "RETURNING \"workId\", \"title\";",
                new WorkMapper(),
                work,
                workId);
    }

    private static class WorkMapper implements RowMapper<Work> {

        @Override
        public Work mapRow(ResultSet resultSet, int i) throws SQLException {
            Work mappedWork = new Work();
            mappedWork.setWorkId(resultSet.getInt("workId"));
            mappedWork.setTitle(resultSet.getString("title"));
            return mappedWork;
        }
    }

}
