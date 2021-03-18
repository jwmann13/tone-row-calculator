package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
import com.tp.toneRowMatrixCalculator.models.ComposerWork;
import com.tp.toneRowMatrixCalculator.models.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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
        ComposerWork results = getComposerWork(workId, composerId);
        return results != null;
    }

    @Override
    public ComposerWork getComposerWork(Integer workId, Integer composerId) {
        ComposerWork results;
        try {
            results = template.queryForObject("SELECT \"workId\", \"composerId\" " +
                            "FROM \"composerWorks\" " +
                            "WHERE \"workId\"=? AND \"composerId\"=?;",
                    new ComposerWorkMapper(),
                    workId,
                    composerId
            );
        } catch (DataAccessException e) {
            return null;
        }
        return results;
    }

    @Override
    public List<ComposerWork> getComposerWorksByWorkId(Integer workId) {
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
    public List<ComposerWork> getComposerWorksByComposerId(Integer composerId) {
        List<ComposerWork> results = template.query("SELECT * FROM \"composerWorks\" WHERE \"composerId\" = ?;",
                new ComposerWorkMapper(),
                composerId
        );

        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }

    @Override
    public ComposerWork createComposerWork(Work work, Composer composer) {
        return template.queryForObject("INSERT INTO \"composerWorks\" (\"composerId\", \"workId\") " +
                        "VALUES (?, ?)" +
                        "RETURNING \"composerId\", \"workId\";",
                new ComposerWorkMapper(),
                composer.getComposerId(),
                work.getWorkId()
        );
    }

    @Override
    public ComposerWork deleteComposerWork(Integer workId, Integer composerId) {
        return template.queryForObject("DELETE FROM \"composerWorks\"" +
                        "WHERE \"composerWorks\".\"workId\" = ?" +
                        "AND \"composerWorks\".\"composerId\" = ?\n" +
                        "RETURNING \"workId\", \"composerId\";",
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
