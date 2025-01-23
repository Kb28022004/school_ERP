package com.poc.school.schoolErp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.school.schoolErp.entities.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}