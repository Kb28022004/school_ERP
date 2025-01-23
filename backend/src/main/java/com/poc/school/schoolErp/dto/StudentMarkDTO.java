package com.poc.school.schoolErp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentMarkDTO {
	
	  private Long studentId;
	    private String name;
	    private String fatherName;
	    private String className;
	    private List<MarkDTO> marks;
	  

}
