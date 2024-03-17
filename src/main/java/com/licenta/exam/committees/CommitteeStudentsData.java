package com.licenta.exam.committees;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CommitteeStudentsData {

    @JsonProperty("committeeId")
    private final int committeeId;

    @JsonProperty("count")
    private final int count;

    @JsonProperty("students")
    private final List<ExamStudentData> students;

    public CommitteeStudentsData() {
        this.committeeId = 0;
        this.students = new ArrayList<>();
        this.count = 0;
    }

    public CommitteeStudentsData(List<ExamStudentData> students, int committeeId) {
        this.committeeId = committeeId;
        this.students = students;
        this.count = students.size();
    }
}
