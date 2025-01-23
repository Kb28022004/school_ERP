package com.poc.school.schoolErp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.school.schoolErp.entities.Classes;
import com.poc.school.schoolErp.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	 Optional<Subject> findByNameAndClassEntity(String name, Classes classEntity);
	 
	 Optional<Subject> findByName(String name);
}

