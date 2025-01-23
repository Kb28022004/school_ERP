package com.poc.school.schoolErp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.school.schoolErp.entities.Mark;
import com.poc.school.schoolErp.entities.Student;

public interface MarkRepository extends JpaRepository<Mark, Long> {
	
	List<Mark> findByStudent(Student Student);
	
	
}