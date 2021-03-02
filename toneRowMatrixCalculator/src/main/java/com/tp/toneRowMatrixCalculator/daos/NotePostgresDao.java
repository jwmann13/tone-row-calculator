package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Note;
import com.tp.toneRowMatrixCalculator.models.NoteInfo;
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
public class NotePostgresDao implements NoteDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public Note createNote(int pitchClass, int orderIndex, Integer toneRowId) {
        return template.queryForObject("INSERT INTO \"notes\" (\"pitchClass\", \"noteOrder\", \"toneRowId\")\n" +
                        "VALUES (?,?,?) " +
                        "RETURNING \"noteId\",\"pitchClass\",\"noteOrder\",\"toneRowId\"",
                new NoteMapper(),
                pitchClass, orderIndex, toneRowId);
    }

    @Override
    public List<Note> getNotesForToneRow(Integer toneRowId) {
        return template.query("SELECT \"noteId\", \"toneRowId\", \"pitchClass\", \"noteOrder\"\n" +
                "FROM \"notes\"\n" +
                "WHERE \"toneRowId\" = ?;",
                new NoteMapper(),
                toneRowId);
    }

    private static class NoteMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet resultSet, int i) throws SQLException {
            Note mappedNote = new Note();
            NoteInfo mappedInfo = NoteInfo.getByValue(resultSet.getInt("pitchClass"));

            mappedNote.setNoteId(resultSet.getInt("noteId"));
            mappedNote.setToneRowId(resultSet.getInt("toneRowId"));
            mappedNote.setNoteInfo(mappedInfo);
            mappedNote.setOrderIndex(resultSet.getInt("noteOrder"));

            return mappedNote;
        }
    }
}
