package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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
public class ComposerPostgresDao implements ComposerDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public Composer createComposer(String composer) {
        return template.queryForObject(
                "INSERT INTO \"composers\" (\"name\") VALUES (?) RETURNING \"composerId\", \"name\"",
                new ComposerMapper(),
                composer
        );
    }

    @Override
    public Map<Integer, Composer> getAllComposers() {
        List<Composer> results;
        try {
            results = template.query(
                    "SELECT \"composerId\", \"name\" FROM \"composers\"",
                    new ComposerMapper()
            );
        } catch (DataAccessException e) {
            return null;
        }

        Map<Integer, Composer> toReturn = new HashMap<>();
        for (Composer toMap : results) {
            toReturn.put(toMap.getComposerId(), toMap);
        }

        return toReturn;
    }

    @Override
    public Composer getComposerById(Integer id) {
        List<Composer> results;
        try {
            results = template.query(
                    "SELECT \"composerId\", \"name\" FROM \"composers\" co WHERE co.\"composerId\" = ?;",
                    new ComposerMapper(),
                    id
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
    public Composer getComposerByName(String composer) {
        List<Composer> results;
        try {
            results = template.query(
                    "SELECT \"composerId\", \"name\" FROM \"composers\" co WHERE co.\"name\" = ?;",
                    new ComposerMapper(),
                    composer
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
    public boolean exists(String composer) {
        return getComposerByName(composer) != null;
    }

    public static class ComposerMapper implements RowMapper<Composer> {

        @Override
        public Composer mapRow(ResultSet resultSet, int i) throws SQLException {
            Composer mappedWork = new Composer();
            mappedWork.setComposerId(resultSet.getInt("composerId"));
            mappedWork.setName( resultSet.getString("name"));
            return mappedWork;
        }
    }
}
