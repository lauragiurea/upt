package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StudentGradesData {

    public int idStud;

    @JsonProperty("studName")
    public String studName;

    @JsonProperty("grades")
    public List<ExamGrade> grades;

    @JsonProperty("status")
    public String status;

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

    @JsonProperty("projectMean")
    public float getProjectMean() {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (ExamGrade grade : grades) {
            sum = sum + grade.projectGrade;
        }
        return sum / grades.size();
    }

    @JsonProperty("knowledgeMean")
    public float getKnowledgeMean() {
        if (grades == null || grades.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (ExamGrade grade : grades) {
            sum = sum + grade.knowledgeGrade;
        }
        return sum / grades.size();
    }

    @JsonProperty("mean")
    public float getMean() {
        return (getProjectMean() + getKnowledgeMean()) / 2;
    }
}
