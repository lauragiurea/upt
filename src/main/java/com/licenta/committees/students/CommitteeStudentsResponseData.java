package com.licenta.committees.students;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CommitteeStudentsResponseData {
    @JsonProperty("count")
    private final int count;

    @JsonProperty("students")
    private final List<CommitteeStudentData> students;

    public CommitteeStudentsResponseData() {
        this.students = new ArrayList<>();
        this.count = 0;
    }

    public CommitteeStudentsResponseData(List<CommitteeStudentData> students) {
        this.students = students;
        this.count = students.size();
    }
}
