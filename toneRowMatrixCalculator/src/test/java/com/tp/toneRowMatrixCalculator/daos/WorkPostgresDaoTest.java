package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("daoTesting")
public class WorkPostgresDaoTest {
    @Autowired
    WorkPostgresDao toTest;

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

    // getAllWorks()
    @Test
    public void getAllWorksTest() {
        Map<Integer, Work> returned = toTest.getAllWorks();

        assertNotNull(returned);
        assertNotNull(returned.get(1));
        assertNull(returned.get(2));
        assertEquals(1, returned.get(1).getWorkId());
        assertEquals("My First Row", returned.get(1).getTitle());
    }

    // getWorkById()
    @Test
    public void getWorkByIdTest() {
        Work returned = toTest.getWorkById(1);

        assertNotNull(returned);
        assertEquals(1, returned.getWorkId());
        assertEquals("My First Row", returned.getTitle());
        assertNull(toTest.getWorkById(2));
    }

    // getWorkByTitle()
    @Test
    public void getWorkByTitleTest() {
        Work returned = toTest.getWorkByTitle("My First Row");

        assertNotNull(returned);
        assertEquals(1, returned.getWorkId());
        assertEquals("My First Row", returned.getTitle());
        assertNull(toTest.getWorkByTitle("Something Else"));
    }

    // createWork()
    @Test
    public void createWorkTest() {
        Work returned = toTest.createWork("Something Else");

        assertNotNull(returned);
        assertEquals(2, returned.getWorkId());
        assertEquals("Something Else", returned.getTitle());
        assertEquals(toTest.getWorkById(2).getWorkId(), returned.getWorkId());
        assertEquals(toTest.getWorkById(2).getTitle(), returned.getTitle());
        assertEquals(toTest.getWorkByTitle("Something Else").getWorkId(), returned.getWorkId());
        assertEquals(toTest.getWorkByTitle("Something Else").getTitle(), returned.getTitle());
    }
}
