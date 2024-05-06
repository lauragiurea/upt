package com.licenta.exam.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamGrade {
    @JsonProperty("profName")
    public String profName;

    @JsonProperty("studName")
    public String studName;

    @JsonProperty("projectGrade")
    public float projectGrade;

    @JsonProperty("knowledgeGrade")
    public float knowledgeGrade;
}
