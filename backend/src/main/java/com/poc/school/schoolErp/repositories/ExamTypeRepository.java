package com.poc.school.schoolErp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.school.schoolErp.entities.ExamType;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {
	
	 Optional<ExamType> findByName(String name);
}