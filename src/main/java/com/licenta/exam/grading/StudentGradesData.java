package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StudentGradesData {

    @JsonProperty("studName")
    public String studName;

    @JsonProperty("grades")
    public List<ExamGrade> grades;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGradesData that = (StudentGradesData) o;
        return Objects.equals(studName, that.studName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studName);
    }
}
