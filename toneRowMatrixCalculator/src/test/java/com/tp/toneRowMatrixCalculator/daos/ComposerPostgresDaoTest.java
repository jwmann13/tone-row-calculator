package com.tp.toneRowMatrixCalculator.daos;

import com.tp.toneRowMatrixCalculator.models.Composer;
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
public class ComposerPostgresDaoTest {
    @Autowired
    ComposerPostgresDao toTest;

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

    //getAllComposers()
    @Test
    public void getAllComposersTest() {
        Map<Integer, Composer> returned = toTest.getAllComposers();

        assertNotNull(returned);
        assertNotNull(returned.get(1));
        assertNull(returned.get(2));
        assertEquals(1, returned.get(1).getComposerId());
        assertEquals("Jeffrey Mann", returned.get(1).getName());
    }

    //getComposerById()
    @Test
    public void getComposerByIdTest() {
        Composer returned = toTest.getComposerById(1);

        assertNotNull(returned);
        assertEquals(1, returned.getComposerId());
        assertEquals("Jeffrey Mann", returned.getName());
        assertNull(toTest.getComposerById(2));
    }

    //getComposerByName()
    @Test
    public void getComposerByNameTest() {
        Composer returned = toTest.getComposerByName("Jeffrey Mann");

        assertNotNull(returned);
        assertEquals(1, returned.getComposerId());
        assertEquals("Jeffrey Mann", returned.getName());
        assertNull(toTest.getComposerByName("Someone Else"));
    }

    //createComposer()
    @Test
    public void createComposerTest() {
        Composer returned = toTest.createComposer("Someone Else");

        assertNotNull(returned);
        assertEquals(2, returned.getComposerId());
        assertEquals("Someone Else", returned.getName());
        assertEquals(toTest.getComposerById(2).getComposerId(), returned.getComposerId());
        assertEquals(toTest.getComposerById(2).getName(), returned.getName());
        assertEquals(toTest.getComposerByName("Someone Else").getComposerId(), returned.getComposerId());
        assertEquals(toTest.getComposerByName("Someone Else").getName(), returned.getName());
    }
}
