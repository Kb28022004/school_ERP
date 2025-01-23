package com.poc.school.schoolErp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.poc.school.schoolErp.dto.MarkDTO;
import com.poc.school.schoolErp.dto.StudentMarkDTO;
import com.poc.school.schoolErp.entities.ExamType;
import com.poc.school.schoolErp.entities.Mark;
import com.poc.school.schoolErp.entities.Student;
import com.poc.school.schoolErp.entities.Subject;
import com.poc.school.schoolErp.repositories.ExamTypeRepository;
import com.poc.school.schoolErp.repositories.MarkRepository;
import com.poc.school.schoolErp.repositories.StudentRepository;
import com.poc.school.schoolErp.repositories.SubjectRepository;
import com.poc.school.schoolErp.service.MarkService;

@RestController
@RequestMapping("/api/marks")
public class MarkController {

    @Autowired
    private MarkRepository markRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private MarkService markService;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private ExamTypeRepository examTypeRepository;

    @GetMapping
    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mark> getMarkById(@PathVariable Long id) {
        Optional<Mark> markOptional = markRepository.findById(id);
        return markOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/student/{id}")
    public ResponseEntity<StudentMarkDTO> getMarksByStudentId(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (!studentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Student not found
        }

        // Fetch the student object
        Student student = studentOptional.get();

        // Fetch marks for the student
        List<Mark> marks = markRepository.findByStudent(student);
        if (marks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Marks not found for student
        }

        // Map marks to MarkDTO, include examType
        List<MarkDTO> markDTOs = marks.stream().map(mark -> {
            // Get the exam type (assuming the mark has a reference to the ExamType entity)
            String examType = mark.getExamType() != null ? mark.getExamType().getName() : "Unknown";

            // Return new MarkDTO including examType
            return new MarkDTO(
                    mark.getSubject().getName(),         // Subject name
                    mark.getObtainMark(),               // Obtain marks
                    mark.getMaximumMark(),              // Maximum marks
                    examType                            // Exam type (e.g.,Half-Yearly, Yearly)
            );
        }).collect(Collectors.toList());

        // Create StudentMarkDTO with student info and marks
        StudentMarkDTO studentMarkDTO = new StudentMarkDTO(
                student.getId(),                     // studentId
                student.getName(),                   // name
                student.getFatherName(),             // fatherName
                student.getClassEntity() != null ? student.getClassEntity().getName() : "Unknown",  // className from Classes entity
                markDTOs                             // List of marks
        );

        
        return ResponseEntity.ok(studentMarkDTO);
    }

    
    @PostMapping
    public ResponseEntity<Mark> createMark(@RequestBody Mark newMark) {
        Mark savedMark = markRepository.save(newMark);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMark);
    }
    
    @PostMapping("/student/{studentId}")
    public ResponseEntity<StudentMarkDTO> createMarksForStudent(@PathVariable Long studentId, @RequestBody StudentMarkDTO studentMarkDTO) {
        // Step 1: Fetch the student by studentId
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // If the student is not found
        }

        Student student = studentOptional.get();

        // Step 2: Save the marks for the student
        List<Mark> savedMarks = studentMarkDTO.getMarks().stream().map(markDTO -> {
            // Retrieve the subject and exam type
            Optional<Subject> subject = subjectRepository.findByName(markDTO.getSubject());
            Optional<ExamType> examType = examTypeRepository.findByName(markDTO.getExamType());

            if (!subject.isPresent() || !examType.isPresent()) {
                throw new IllegalArgumentException("Subject or Exam Type not found");
            }

            // Create and save the Mark entity
            Mark mark = new Mark();
            mark.setStudent(student);
            mark.setSubject(subject.get());
            mark.setObtainMark(markDTO.getObtainMark());
            mark.setMaximumMark(markDTO.getMaximumMark());
            mark.setExamType(examType.get());

            return markRepository.save(mark); // Save each mark record and return the saved mark
        }).collect(Collectors.toList());

        // Step 3: Convert saved marks to DTO format
        List<MarkDTO> markDTOs = savedMarks.stream().map(mark -> new MarkDTO(
                mark.getSubject().getName(),
                mark.getObtainMark(),
                mark.getMaximumMark(),
                mark.getExamType().getName() // Assuming you have an ExamType object to get the name
        )).collect(Collectors.toList());

        // Step 4: Create the response DTO
        StudentMarkDTO savedStudentMarkDTO = new StudentMarkDTO(
                student.getId(),
                student.getName(),
                student.getFatherName(),
                student.getClassEntity() != null ? student.getClassEntity().getName() : "Unknown",
                markDTOs
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentMarkDTO);
    }

    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Mark> updateMark(@PathVariable Long id, @RequestBody Mark updatedMark) {
        if (!markRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedMark.setId(id);
        markRepository.save(updatedMark);
        return ResponseEntity.ok(updatedMark);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        if (!markRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        markRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
