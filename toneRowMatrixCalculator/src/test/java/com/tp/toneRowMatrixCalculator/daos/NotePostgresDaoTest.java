package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Note;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("daoTesting")
public class NotePostgresDaoTest {
    @Autowired
    NotePostgresDao toTest;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setup() {
        // 1. TRUNCATE tables
        template.update("TRUNCATE \"notes\", \"toneRows\", \"composerWorks\", " +
                "\"works\", \"composers\" RESTART IDENTITY;\n");

        // 2. INSERT testing data
        template.update("INSERT INTO \"works\" (\"title\") VALUES ('My First Row');");
        template.update("INSERT INTO \"composers\" (\"name\") VALUES ('Jeffrey Mann');");
        template.update("INSERT INTO \"composerWorks\" (\"composerId\", \"workId\") VALUES ('1', '1');");
        template.update("INSERT INTO \"toneRows\" (\"workId\") VALUES ('1');");
        template.update("INSERT INTO \"notes\" (\"pitchClass\", \"noteOrder\", \"toneRowId\")\n" +
                "VALUES ('0', '0', '1'), ('1', '1', '1'), ('2', '2', '1'), ('3', '3', '1'),\n" +
                "('4', '4', '1'), ('5', '5', '1'), ('6', '6', '1'), ('7', '7', '1'),\n" +
                "('8', '8', '1'), ('9', '9', '1'), ('10', '10', '1'), ('11', '11', '1');");
        template.update("INSERT INTO \"toneRows\" (\"workId\") VALUES ('1');");
    }

    @Test
    public void createNoteTest() {
        Note returned = toTest.createNote(0, 0, 2);

        assertNotNull(returned);
        assertEquals(0, returned.getPitchClass());
        assertEquals(0, returned.getOrderIndex());
        assertEquals(2, returned.getToneRowId());
        assertEquals(13, returned.getNoteId());
        assertEquals("C", returned.getNaturalName());
        assertNull(returned.getSharpName());
        assertNull(returned.getFlatName());
        assertFalse(returned.isAccidental());
    }

    @Test
    public void getNotesForToneRowTest() {
        List<Note> returned = toTest.getNotesForToneRow(1);

        assertNotNull(returned);
        assertEquals(12, returned.size());
        assertEquals(0, returned.get(0).getOrderIndex());
        assertEquals(1, returned.get(0).getToneRowId());
        assertEquals(1, returned.get(0).getNoteId());
        assertEquals("C", returned.get(0).getNaturalName());
        assertNull(returned.get(0).getSharpName());
        assertNull(returned.get(0).getFlatName());
        assertFalse(returned.get(0).isAccidental());
    }

    @Test
    public void getNotesForToneRowTestNoNotes() {
        List<Note> noNotes = toTest.getNotesForToneRow(2);

        assertTrue(noNotes.isEmpty());
    }
}
