package com.poc.school.schoolErp.specification;

import org.springframework.data.jpa.domain.Specification;

import com.poc.school.schoolErp.entities.Student;

public class StudentSpecification {
	
    public static Specification<Student> hasSessionId(Long sessionId) {
        return (root, query, criteriaBuilder) -> sessionId == null ? null : criteriaBuilder.equal(root.get("session").get("id"), sessionId);
    }

    public static Specification<Student> hasClassId(Long classId) {
        return (root, query, criteriaBuilder) -> classId == null ? null : criteriaBuilder.equal(root.get("classEntity").get("id"), classId);
    }

    public static Specification<Student> hasSectionId(Long sectionId) {
        return (root, query, criteriaBuilder) -> sectionId == null ? null : criteriaBuilder.equal(root.get("section").get("id"), sectionId);
    }

}
