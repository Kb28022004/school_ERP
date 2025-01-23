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

import com.poc.school.schoolErp.entities.Section;
import com.poc.school.schoolErp.repositories.SectionRepository;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionRepository sectionRepository;

    @GetMapping
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        return sectionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section newSection) {
        Section savedSection = sectionRepository.save(newSection);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section updatedSection) {
        if (!sectionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedSection.setId(id);
        sectionRepository.save(updatedSection);
        return ResponseEntity.ok(updatedSection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        if (!sectionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sectionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}