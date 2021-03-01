package com.tp.toneRowMatrixCalculator.services;

import com.tp.toneRowMatrixCalculator.daos.NoteInMemDao;
import com.tp.toneRowMatrixCalculator.daos.ToneRowInMemDao;
import com.tp.toneRowMatrixCalculator.exceptions.InvalidIdException;
import com.tp.toneRowMatrixCalculator.models.ToneRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("serviceTesting")
public class MatrixServiceTest {
    @Autowired
    MatrixService toTest;

    @BeforeEach
    public void setup() {
//        toTest.toneRowDao.clear();
//        toTest.noteDao.clear();

        toTest.createWork("My First Row");
        toTest.createComposer("Jeffrey Mann");
        toTest.createToneRow(new Integer[] {0,1,2,3,4,5,6,7,8,9,10,11}, 1);
    }

    //getAllToneRows()
    @Test
    public void getAllToneRowsTest() {
        Map<Integer, ToneRow> returned = toTest.getAllToneRows();

        assertNotNull(returned);
        assertNotNull(returned.get(1));
        assertEquals(1, returned.get(1).getToneRowId());
        assertEquals(1, returned.get(1).getWorkId());
        assertEquals(0, returned.get(1).getNoteOrder()[0].getPitchClass());
        assertEquals("C", returned.get(1).getNoteOrder()[0].getNaturalName());
        assertNull(returned.get(1).getNoteOrder()[0].getFlatName());
        assertNull(returned.get(1).getNoteOrder()[0].getSharpName());
        assertFalse(returned.get(1).getNoteOrder()[0].isAccidental());
    }

    //getToneRowById()
    @Test
    public void getToneRowByIdTest() throws InvalidIdException {
        ToneRow returned = toTest.getToneRowById(1);

        assertNotNull(returned);
        assertEquals(1, returned.getToneRowId());
        assertEquals(1, returned.getWorkId());
        assertEquals(0, returned.getNoteOrder()[0].getPitchClass());
        assertEquals("C", returned.getNoteOrder()[0].getNaturalName());
        assertNull(returned.getNoteOrder()[0].getFlatName());
        assertNull(returned.getNoteOrder()[0].getSharpName());
        assertFalse(returned.getNoteOrder()[0].isAccidental());
    }
}
