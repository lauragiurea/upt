package com.licenta.exam.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentStats {

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
}
