package com.poc.school.schoolErp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkDTO {

	private String subject;
	private Integer obtainMark;
	private Integer maximumMark;
	private String result;
	private String examType;
	
	 public MarkDTO(String subject, Integer obtainMark, Integer maximumMark, String examType) {
	        this.subject = subject;
	        this.obtainMark = obtainMark;
	        this.maximumMark = maximumMark;
	        this.examType = examType;
	        calculateResult(); // Calculate result when the object is created
	    }
	
	public void calculateResult() {
		if (obtainMark != null && maximumMark != null) {
			double percentage = (obtainMark.doubleValue() / maximumMark.doubleValue()) * 100;
			this.result = percentage >= 33 ? "Pass" : "Fail";
		} else {
			this.result = "Result not available";
		}
	}





	
	

}
