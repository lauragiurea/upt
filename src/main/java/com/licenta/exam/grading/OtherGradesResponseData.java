package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OtherGradesResponseData {

    @JsonProperty("committeeId")
    public int committeeId;

    @JsonProperty("professors")
    public List<String> professors;

    @JsonProperty("students")
    public List<StudentGradesData> grades;

    public OtherGradesResponseData() {
        this.committeeId = 0;
        this.grades = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    public OtherGradesResponseData(int committeeId, List<StudentGradesData> grades, List<String> professors) {
        this.committeeId = committeeId;
        this.grades = grades;
        this.professors = professors;
    }
}
