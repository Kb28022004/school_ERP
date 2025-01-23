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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.school.schoolErp.entities.Student;
import com.poc.school.schoolErp.repositories.StudentRepository;
import com.poc.school.schoolErp.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        
        return studentRepository.findAll();
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create Student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student newStudent) {
        Student savedStudent = studentRepository.save(newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedStudent.setId(id);  // Set the student ID for the update
        studentRepository.save(updatedStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public List<Student> getStudents(
        @RequestParam(required = false) Long sessionId,
        @RequestParam(required = false) Long classId,
        @RequestParam(required = false) Long sectionId) {

        // Fetch students with the provided filters
        return studentService.getStudentsByFilters(sessionId, classId, sectionId);
    }
 
}
