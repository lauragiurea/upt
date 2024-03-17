package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeRequestData {
    @JsonProperty("knowledgeGrade")
    public float knowledgeGrade;

    @JsonProperty("projectGrade")
    public float projectGrade;

    @JsonProperty("studentId")
    public int studentId;
}
