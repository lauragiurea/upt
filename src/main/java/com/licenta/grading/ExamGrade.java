package com.licenta.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamGrade {
    @JsonProperty("projectGrade")
    public float projectGrade;

    @JsonProperty("knowledgeGrade")
    public String knowledgeGrade;

    @JsonProperty("mean")
    public String mean;
}
