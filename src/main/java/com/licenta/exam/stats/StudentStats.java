package com.licenta.exam.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentStats {

    public int studentId;

    @JsonProperty("studName")
    public String studName;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("committeeId")
    public int committeeId;

    @JsonProperty("examGrade")
    public float examGrade;

    @JsonProperty("schoolGrade")
    public float schoolGrade;

    @JsonProperty("coordGrade")
    public float coordGrade;

    @JsonProperty("coordName")
    public String coordName;

    @JsonProperty("projectMean")
    public float projectMean;

    @JsonProperty("knowledgeMean")
    public float knowledgeMean;
}
