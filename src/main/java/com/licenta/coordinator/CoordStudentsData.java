package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CoordStudentsData {

    @JsonProperty("count")
    public int count;

    @JsonProperty("students")
    public List<CoordStudentData> students;

    public CoordStudentsData() {
        this.count = 0;
        this.students = new ArrayList<>();
    }

    public CoordStudentsData(List<CoordStudentData> students) {
        this.count = students.size();
        this.students = students;
    }
}
