package com.poc.school.schoolErp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.school.schoolErp.entities.ExamType;
import com.poc.school.schoolErp.repositories.ExamTypeRepository;

@RestController
@RequestMapping("/api/examtypes")
public class ExamTypeController {

    @Autowired
    private ExamTypeRepository examTypeRepository;

    @GetMapping
    public List<ExamType> getAllExamTypes() {
        return examTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamType> getExamTypeById(@PathVariable Long id) {
        Optional<ExamType> examTypeOptional = examTypeRepository.findById(id);
        return examTypeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ExamType> createExamType(@RequestBody ExamType newExamType) {
        ExamType savedExamType = examTypeRepository.save(newExamType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExamType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamType> updateExamType(@PathVariable Long id, @RequestBody ExamType updatedExamType) {
        if (!examTypeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedExamType.setId(id);
        examTypeRepository.save(updatedExamType);
        return ResponseEntity.ok(updatedExamType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamType(@PathVariable Long id) {
        if (!examTypeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        examTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}