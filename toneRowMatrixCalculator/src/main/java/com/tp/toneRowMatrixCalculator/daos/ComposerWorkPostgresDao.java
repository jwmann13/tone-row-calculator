package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.ComposerWork;
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
        List<ComposerWork> results = getComposerWork(workId, composerId);
        if (results == null) return false;
        for (ComposerWork cw : results) {
            if (cw == null) return false;
        }
        return true;
    }

    @Override
    public List<ComposerWork> getComposerWork(Integer workId, Integer composerId)  {
        List<ComposerWork> results = template.query("SELECT \"workId\", \"composerId\" " +
                        "FROM \"composerWorks\" " +
                        "WHERE \"workId\"=? AND \"composerId\"=?;",
                new ComposerWorkMapper(),
                workId,
                composerId
        );

        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }

    @Override
    public List<ComposerWork> getComposerWorkByWorkId(Integer workId) {
        List<ComposerWork> results = template.query("SELECT * FROM \"composerWorks\" WHERE \"workId\" = ?;",
                new ComposerWorkMapper(),
                workId
        );

        if (results.isEmpty()) {
            return null;
        } else {
            return results;
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

    private static class ComposerWorkMapper implements RowMapper<ComposerWork> {

        @Override
        public ComposerWork mapRow(ResultSet resultSet, int i) throws SQLException {
            ComposerWork mapped = new ComposerWork();
            mapped.setWorkId(resultSet.getInt("workId"));
            mapped.setComposerId(resultSet.getInt("composerId"));
            return mapped;
        }
    }
}
