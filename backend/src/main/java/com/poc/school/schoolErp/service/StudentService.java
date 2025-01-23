package com.poc.school.schoolErp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.poc.school.schoolErp.entities.Student;
import com.poc.school.schoolErp.repositories.StudentRepository;
import com.poc.school.schoolErp.specification.StudentSpecification;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> getStudentsByFilters(Long sessionId, Long classId, Long sectionId) {
		Specification<Student> spec = Specification.where(StudentSpecification.hasSessionId(sessionId))
				.and(StudentSpecification.hasClassId(classId)).and(StudentSpecification.hasSectionId(sectionId));

		return studentRepository.findAll(spec);
	}
}
