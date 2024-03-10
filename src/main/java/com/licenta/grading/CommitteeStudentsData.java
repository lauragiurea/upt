package com.licenta.grading;

import java.util.ArrayList;
import java.util.List;

public class CommitteeStudentsData {
    private final List<Student> students;

    public CommitteeStudentsData() {
        this.students = new ArrayList<>();
    }

    public CommitteeStudentsData(List<Student> students) {
        this.students = students;
    }

    public void addStudents(List<Student> students) {
        this.students.addAll(students);
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
