package com.poc.school.schoolErp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Student")
public class Student {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fatherName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes classEntity;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
   
    
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
    
    
 
}