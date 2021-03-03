package com.tp.toneRowMatrixCalculator.controllers;

import com.tp.toneRowMatrixCalculator.exceptions.InvalidIdException;
import com.tp.toneRowMatrixCalculator.models.*;
import com.tp.toneRowMatrixCalculator.services.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class MatrixController {
    @Autowired
    MatrixService service;

    @GetMapping("/matrix")
    public ResponseEntity getAllMatrices() {
        Map<Integer, Matrix> toReturn;
        try {
            toReturn = service.getAllMatrices();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/tonerow")
    public ResponseEntity getAllToneRows() {
        Map<Integer, ToneRow> toReturn;
        try {
            toReturn = service.getAllToneRows();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/matrix", params = {"id"})
    public ResponseEntity getMatrixById(@RequestParam Integer id) {
        Matrix toReturn;
        try {
            toReturn = service.getMatrixById(id);
        } catch (InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/tonerow", params = {"id"})
    public ResponseEntity getToneRowById(@RequestParam Integer id) {
        ToneRow toReturn;
        try {
            toReturn = service.getToneRowById(id);
        } catch (InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "tonerow/meta", params = {"id"})
    public ResponseEntity getToneRowMeta(@RequestParam Integer id) {
        ToneRowMeta toReturn;
        try {
            toReturn = service.getToneRowMeta(id);
        } catch (InvalidIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/work")
    public ResponseEntity getAllWorks() {
        Map<Integer, Work> toReturn;
        try {
            toReturn = service.getAllWorks();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/work", params = {"id"})
    public ResponseEntity getWorkById(@RequestParam Integer id) {
        Work toReturn;
        try {
            toReturn = service.getWorkById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/work", params = {"title"})
    public ResponseEntity getWorkByTitle(@RequestParam String title) {
        Work toReturn;
        try {
            toReturn = service.getWorkByTitle(title);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/composer")
    public ResponseEntity getAllComposers() {
        Map<Integer, Composer> toReturn;
        try {
            toReturn = service.getAllComposers();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/composer", params = {"id"})
    public ResponseEntity getComposerById(@RequestParam Integer id) {
        Composer toReturn;
        try {
            toReturn = service.getComposerById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/composer", params = {"name"})
    public ResponseEntity getComposerByName(@RequestParam String name) {
        Composer toReturn;
        try {
            toReturn = service.getComposerByName(name);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping(value = "/composerwork", params = {"id"})
    public ResponseEntity getComposerWork(@RequestParam Integer id) {
        List<ComposerWork> toReturn;
        try {
            toReturn = service.getComposerWorksByWorkId(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/tonerow")
    public ResponseEntity createToneRow(@RequestBody ToneRowRequest newToneRow) {
        ToneRow toReturn;
        Integer[] noteOrder = newToneRow.noteOrder;
        Integer workId = newToneRow.workId;
        try {
            toReturn = service.createToneRow(noteOrder, workId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/work")
    public ResponseEntity createWork(@RequestBody WorkRequest newWork) {
        Work toReturn;
        toReturn = service.createWork(newWork.getTitle());
        if (toReturn == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create work");
        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/composer")
    public ResponseEntity createComposer(@RequestBody ComposerRequest newComposer) {
        Composer toReturn;
        toReturn = service.createComposer(newComposer.getName());
        if (toReturn == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create composer");
        return ResponseEntity.ok(toReturn);
    }

}
