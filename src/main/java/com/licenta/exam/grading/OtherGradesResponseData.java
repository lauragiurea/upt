package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OtherGradesResponseData {

    @JsonProperty("count")
    public int count;

    @JsonProperty("grades")
    public List<ExamGrade> grades;

    public OtherGradesResponseData() {
        this.count = 0;
        this.grades = new ArrayList<>();
    }

    public OtherGradesResponseData(List<ExamGrade> grades) {
        this.count = grades.size();
        this.grades = grades;
    }
}
