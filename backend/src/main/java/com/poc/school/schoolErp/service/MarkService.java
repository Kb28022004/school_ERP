package com.poc.school.schoolErp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poc.school.schoolErp.dto.MarkDTO;

@Service
public class MarkService {

    public String calculateGrade(List<MarkDTO> marks) {
        if (marks == null || marks.isEmpty()) {
            return "Grade not available";
        }

        double totalMarks = marks.stream()
                                 .mapToDouble(MarkDTO::getObtainMark)
                                 .sum();
        double totalMaxMarks = marks.stream()
                                    .mapToDouble(MarkDTO::getMaximumMark)
                                    .sum();

        double average = (totalMarks / totalMaxMarks) * 100;

        if (average >= 80) {
            return "Grade A";
        } else if (average >= 60) {
            return "Grade B";
        } else if (average >= 40) {
            return "Grade C";
        } else {
            return "Grade D";
        }
    }
}