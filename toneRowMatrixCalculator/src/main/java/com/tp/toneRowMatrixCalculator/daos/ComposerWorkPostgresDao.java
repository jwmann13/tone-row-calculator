package com.tp.toneRowMatrixCalculator.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile({"production", "daoTesting"})
public class ComposerWorkPostgresDao implements ComposerWorkDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public boolean exists(Integer workId, Integer composerId) {
        Integer[] ids = getComposerWork(workId, composerId);
        if (ids == null) return false;
        for (Integer id :
                ids) {
            if (id == null) return false;
        }
        return true;
    }

    @Override
    public Integer[] getComposerWork(Integer workId, Integer composerId)  {
        List<Integer[]> results = template.query("SELECT \"workId\", \"composerId\" " +
                        "FROM \"composerWorks\" " +
                        "WHERE \"workId\"=? AND \"composerId\"=?;",
                new ComposerWorkMapper(),
                workId,
                composerId
        );

        if (results.isEmpty()) {
            return null;
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void createComposerWork(Integer workId, Integer composerId) {
        template.query("INSERT INTO \"composerWorks\" (\"composerId\", \"workId\") " +
                        "VALUES (?, ?);",
                new ComposerWorkMapper(),
                workId,
                composerId
        );
    }

    private static class ComposerWorkMapper implements RowMapper<Integer[]> {

        @Override
        public Integer[] mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer[] ids = new Integer[2];
            ids[0] = resultSet.getInt("workId");
            ids[1] = resultSet.getInt("composerId");
            return ids;
        }
    }
}
