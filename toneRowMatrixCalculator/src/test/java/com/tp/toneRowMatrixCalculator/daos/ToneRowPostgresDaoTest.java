package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Matrix;
import com.tp.toneRowMatrixCalculator.models.Note;
import com.tp.toneRowMatrixCalculator.models.ToneRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("daoTesting")
public class ToneRowPostgresDaoTest {
    @Autowired
    ToneRowPostgresDao toTest;

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
    }

    // getAllToneRows()
    @Test
    public void getAllToneRowsTest() {
        Map<Integer, ToneRow> returned = toTest.getAllToneRows();
        Note[] notes = returned.get(1).getNoteOrder();

        assertNotNull(returned);
        assertEquals(1, returned.size());
        assertEquals(1, returned.get(1).getToneRowId());
        assertEquals(0, notes[0].getPitchClass());
        assertEquals("C", notes[0].getNaturalName());
        assertNull(notes[0].getSharpName());
        assertNull(notes[0].getFlatName());
        assertFalse(notes[0].isAccidental());
    }
    //TODO: more get toneRow tests
    
    // getToneRowById()
    @Test
    public void getToneRowByIdTest() {
        ToneRow returned = toTest.getToneRowById(1);
        Note[] notes = returned.getNoteOrder();

        assertNotNull(returned);
        assertEquals(1, returned.getToneRowId());
        assertEquals(0, notes[0].getPitchClass());
        assertEquals("C", notes[0].getNaturalName());
        assertNull(notes[0].getSharpName());
        assertNull(notes[0].getFlatName());
        assertFalse(notes[0].isAccidental());
    }
    //TODO: more get toneRow tests
    
    // createToneRow()
    @Test
    public void createToneRowGoldenPathTest() {
        ToneRow returned = toTest.createToneRow(1);

        assertEquals(2, returned.getToneRowId());
        assertEquals(1, returned.getWorkId());
        assertNull(returned.generateMatrix());
        assertNull(returned.getNoteOrder());
    }
    //TODO: more create toneRow tests with bad data/arguments

}
