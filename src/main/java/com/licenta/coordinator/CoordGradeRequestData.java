package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordGradeRequestData {

    @JsonProperty("studentId")
    public int studentId;

    @JsonProperty("grade")
    public float grade;
}
