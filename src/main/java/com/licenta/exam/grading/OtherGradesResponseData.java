package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OtherGradesResponseData {

    @JsonProperty("professors")
    public List<String> professors;

    @JsonProperty("students")
    public List<StudentGradesData> grades;

    public OtherGradesResponseData() {
        this.grades = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    public OtherGradesResponseData(List<StudentGradesData> grades, List<String> professors) {
        this.grades = grades;
        this.professors = professors;
    }
}
