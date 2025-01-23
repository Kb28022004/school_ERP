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

import com.poc.school.schoolErp.entities.Classes;
import com.poc.school.schoolErp.repositories.ClassesRepository;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {

	@Autowired
	private ClassesRepository classRepository;

	@GetMapping
	public List<Classes> getAllClasses() {
		List<Classes> cls = classRepository.findAll();
		return cls;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Classes> getClassById(@PathVariable Long id) {
		Optional<Classes> classOptional = classRepository.findById(id);
		return classOptional.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<Classes> createClass(@RequestBody Classes newClass) {
		try {
			Classes savedClass = classRepository.save(newClass);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Classes> updateClass(@PathVariable Long id, @RequestBody Classes updatedClass) {
		if (!classRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		updatedClass.setId(id);
		classRepository.save(updatedClass);
		return ResponseEntity.ok(updatedClass);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
		if (!classRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		classRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}