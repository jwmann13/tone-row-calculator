package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.ToneRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class ToneRowPostgresDao implements ToneRowDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public Map<Integer, ToneRow> getAllToneRows() {
        List<ToneRow> toneRowList = template.query(
                "SELECT \"toneRowId\",\"workId\" FROM \"toneRows\";",
                new ToneRowMapper());

        Map<Integer, ToneRow> toReturn = new HashMap<>();
        for (ToneRow toMap : toneRowList) {
            toReturn.put(toMap.getToneRowId(), toMap);
        }

        return toReturn;
    }

    @Override
    public ToneRow getToneRowById(Integer toneRowId) {
        ToneRow result;
        try {
            result = template.queryForObject(
                    "SELECT \"toneRowId\", \"workId\" FROM \"toneRows\" WHERE \"toneRowId\"=?;",
                    new ToneRowMapper(),
                    toneRowId);

            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public ToneRow createToneRow(Integer workId) {
        return template.queryForObject("INSERT INTO \"toneRows\" (\"workId\") " +
                        "VALUES (?) RETURNING \"toneRowId\",\"workId\";",
                new ToneRowMapper(),
                workId);
    }

    private static class ToneRowMapper implements RowMapper<ToneRow> {
        @Override
        public ToneRow mapRow(ResultSet resultSet, int i) throws SQLException {
            ToneRow mappedToneRow = new ToneRow();
            mappedToneRow.setToneRowId(resultSet.getInt("toneRowId"));
            mappedToneRow.setWorkId(resultSet.getInt("workId"));
            return mappedToneRow;
        }
    }

}
