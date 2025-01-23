package com.poc.school.schoolErp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.poc.school.schoolErp.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>,JpaSpecificationExecutor<Student>{
	
	
	/*
	 * List<Student> findBySessionId(Long sessionId);
	 * 
	 * 
	 * List<Student> findBySessionIdAndClassEntityId(Long sessionId, Long classId);
	 * 
	 * 
	 * List<Student> findBySessionIdAndClassEntityIdAndSectionId(Long sessionId,
	 * Long classId, Long sectionId);
	 */
	
	Optional<Student> findById(Long studentId);
	  



}