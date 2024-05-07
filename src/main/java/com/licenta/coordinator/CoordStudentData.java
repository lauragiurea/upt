package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordStudentData {

    @JsonProperty("email")
    public String email;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("schoolGrade")
    public float schoolGrade;

    @JsonProperty("grade")
    public float grade;
}
