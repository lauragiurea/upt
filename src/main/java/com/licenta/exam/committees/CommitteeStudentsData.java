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
    private final List<Student> students;

    public CommitteeStudentsData() {
        this.committeeId = 0;
        this.students = new ArrayList<>();
        this.count = 0;
    }

    public CommitteeStudentsData(List<Student> students, int committeeId) {
        this.committeeId = committeeId;
        this.students = students;
        this.count = students.size();
    }

    public void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
