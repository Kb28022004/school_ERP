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

import com.poc.school.schoolErp.entities.Subject;
import com.poc.school.schoolErp.repositories.ClassesRepository;
import com.poc.school.schoolErp.repositories.SubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRepository;


	@Autowired
	private ClassesRepository classesRepository;

	@GetMapping
	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
		Optional<Subject> subjectOptional = subjectRepository.findById(id);
		return subjectOptional.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<Subject> createSubject(@RequestBody Subject newSubject) {
		Subject savedSubject = subjectRepository.save(newSubject);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) {
		if (!subjectRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		updatedSubject.setId(id);
		subjectRepository.save(updatedSubject);
		return ResponseEntity.ok(updatedSubject);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
		if (!subjectRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		subjectRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}